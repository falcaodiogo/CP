package ua.diogo.cp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import ua.diogo.cp.data.database.entity.User
import ua.diogo.cp.data.retrofit.entity.Jorney


@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

//    suspend fun addTrainToUser(userId: String, newJorney: Jorney) {
//        val user = getUserById(userId.toLong())
//        user.let {
//            val updatedTrains = it.savedTrains + newJorney
//            val updatedUser = it.copy(savedTrains = updatedTrains)
//            updateUser(updatedUser)
//        }
//    }

    @Query("SELECT * FROM USER WHERE userId = :id")
    fun getUserById(id: Long): User

    @Query("SELECT * FROM USER WHERE name = :name")
    fun getUserByName(name: String): User

    @Query("SELECT * FROM USER WHERE email = :email")
    fun getUserByEmail(email: String): User

}