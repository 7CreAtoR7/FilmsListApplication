package ru.ilya.filmslist.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.ilya.filmslist.data.database.converters.ConverterCountry
import ru.ilya.filmslist.data.database.converters.ConverterGenre
import ru.ilya.filmslist.domain.models.Country
import ru.ilya.filmslist.domain.models.Genre

@Entity(tableName = "films")
data class FilmItemDBModel(

    @PrimaryKey
    private val id: Long,

    private val name: String,

    private val year: String,

    @TypeConverters(ConverterCountry::class)
    val countries: List<Country>? = emptyList(),

    @TypeConverters(ConverterGenre::class)
    private val genres: List<Genre>? = emptyList(),

    private val posterUrl: String,

    private val isFavourite: Boolean = false
)
