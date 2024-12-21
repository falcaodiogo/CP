package ua.diogo.cp.data.initialization

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.diogo.cp.data.dao.UserDao
import ua.diogo.cp.data.entity.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = true
)
abstract class CPDatabase: RoomDatabase() {

    abstract val userDao: UserDao

}