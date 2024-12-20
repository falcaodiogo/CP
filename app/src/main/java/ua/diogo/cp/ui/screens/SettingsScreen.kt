package ua.diogo.cp.ui.screens

import android.content.Context
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.authentication.UserData

@Composable
fun SettingsScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    context: Context,
    googleAuthUiClient: GoogleAuthUiClient
) {
}