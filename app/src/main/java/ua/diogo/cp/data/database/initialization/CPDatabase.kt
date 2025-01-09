package ua.diogo.cp.data.database.initialization

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.diogo.cp.data.database.converters.Converters
import ua.diogo.cp.data.database.dao.UserDao
import ua.diogo.cp.data.database.entity.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class CPDatabase: RoomDatabase() {

    abstract val userDao: UserDao

}