package ua.diogo.cp.authentication

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import co.yml.charts.common.extensions.isNotNull
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ua.diogo.cp.R
import ua.diogo.cp.data.database.dao.UserDao
import ua.diogo.cp.data.database.entity.User
import ua.diogo.cp.data.retrofit.entity.Jorney

class GoogleAuthUiClient(
    val context: Context,
    private val oneTapClient: SignInClient,
    private val userDao: UserDao
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user

            if (user?.email != null && user.displayName != null) {
                Log.i("GoogleAuthUiClient", "Saving user")
                saveUser(user.email!!, user.displayName!!)
            }

            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    private suspend fun saveUser(email: String, userName: String, stepsGoal: Int = 500) {
        // Switch to a IO Dispatcher for a background work cause room doesn't allow operations
        // involving the database in the UI thread cause it might block it
        withContext(Dispatchers.IO) {
            val possibleUserByEmail = userDao.getUserByEmail(email)
            val possibleUserByName = userDao.getUserByName(userName)

            if (!possibleUserByName.isNotNull() && !possibleUserByEmail.isNotNull()) {
                val newUser = User(name = userName, email = email, savedTrains = emptyList())
                userDao.upsertUser(newUser)
                Log.i("StoreUser", "User saved with success")
            } else {
                Log.i("StoreUser", "User already exists")
            }
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    // add a jorney to the user only if it's not already saved (same code)
    suspend fun addJorneyToUser(jorney: Jorney) {
        withContext(Dispatchers.IO) {
            val user = userDao.getUserByEmail(auth.currentUser?.email!!)
            if (!user.savedTrains.any { it.trainNumber == jorney.trainNumber }) {
                val updatedUser = user.copy(savedTrains = user.savedTrains + jorney)
                userDao.upsertUser(updatedUser)
            }
        }
    }

    // remove a jorney from the user (same code)
    suspend fun removeJorneyFromUser(jorney: Jorney) {
        withContext(Dispatchers.IO) {
            val user = userDao.getUserByEmail(auth.currentUser?.email!!)
            val updatedUser =
                user.copy(savedTrains = user.savedTrains.filter { it.trainNumber != jorney.trainNumber })
            userDao.upsertUser(updatedUser)
        }
    }

    suspend fun cleanSavedJorneys() {
        withContext(Dispatchers.IO) {
            val user = userDao.getUserByEmail(auth.currentUser?.email!!)
            val updatedUser = user.copy(savedTrains = emptyList())
            userDao.upsertUser(updatedUser)
        }
    }

    // get the user's saved jorneys
    suspend fun getSavedJorneys(): List<Jorney> {
        return withContext(Dispatchers.IO) {
            val user = userDao.getUserByEmail(auth.currentUser?.email!!)
            user.savedTrains
        }
    }

    // is joorney saved by the user (for jornies, check if the code is the same)
    suspend fun isJorneySaved(jorney: Jorney): Boolean {
        return withContext(Dispatchers.IO) {
            val user = userDao.getUserByEmail(auth.currentUser?.email!!)
            user.savedTrains.any { it.trainNumber == jorney.trainNumber }
        }
    }
}