package ru.ilya.filmslist.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.ilya.filmslist.domain.models.Genre

class ConverterGenre {

    private val gson = Gson()

    @TypeConverter
    fun listToString(data: List<Genre>?): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun stringToList(genre: String): List<Genre>? {
        val listType = object : TypeToken<List<Genre>?>() {}.type
        return gson.fromJson<List<Genre>?>(genre, listType)
    }

}