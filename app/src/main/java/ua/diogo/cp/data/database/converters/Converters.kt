package ua.diogo.cp.data.database.converters

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import ua.diogo.cp.data.retrofit.entity.Jorney

class Converters {
    @TypeConverter
    fun fromJorneyList(value: List<Jorney>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toJorneyList(value: String): List<Jorney>? {
        val listType = object : TypeToken<List<Jorney>>() {}.type
        return Gson().fromJson(value, listType)
    }
}