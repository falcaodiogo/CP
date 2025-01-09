package ua.diogo.cp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.crashlytics.buildtools.reloc.javax.annotation.Nonnull
import ua.diogo.cp.data.retrofit.entity.Jorney
import ua.diogo.cp.data.retrofit.entity.Train

@Entity(tableName = "USER")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long? = null,
    @Nonnull
    val name: String,
    @Nonnull
    val email: String,

//    @ColumnInfo(name = "saved_trains")
//    val savedTrains: List<Jorney> = emptyList()
)


