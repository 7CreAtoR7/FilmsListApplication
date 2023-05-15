package ru.ilya.filmslist.data.network

import com.google.gson.annotations.SerializedName
import ru.ilya.filmslist.domain.models.Country
import ru.ilya.filmslist.domain.models.Genre

data class DetailedFilmDTO(

    @SerializedName("kinopoiskId")
    val kinopoiskID: Long? = null,

    @SerializedName("nameRu")
    val nameRu: String? = null,

    @SerializedName("posterUrl")
    val posterURL: String? = null,

    @SerializedName("year")
    val year: Long? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("countries")
    val countries: List<Country>? = null,

    @SerializedName("genres")
    val genres: List<Genre>? = null,


    )
