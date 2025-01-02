package ua.diogo.cp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.crashlytics.buildtools.reloc.javax.annotation.Nonnull

@Entity(tableName = "USER")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long? = null,
    @Nonnull
    val name: String,
    @Nonnull
    val email: String,
)


