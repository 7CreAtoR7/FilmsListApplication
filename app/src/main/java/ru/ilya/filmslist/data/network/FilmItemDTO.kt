package ru.ilya.filmslist.data.network

import com.google.gson.annotations.SerializedName
import ru.ilya.filmslist.domain.models.Country
import ru.ilya.filmslist.domain.models.Genre

data class FilmItemDTO(

    @SerializedName("filmId")
    val filmID: Long? = null,

    @SerializedName("nameRu")
    val nameRu: String? = null,

    @SerializedName("year")
    val year: String? = null,

    @SerializedName("countries")
    val countries: List<Country>? = null,

    @SerializedName("genres")
    val genres: List<Genre>? = null,

    @SerializedName("posterUrl")
    val posterURL: String? = null,

    val favourite: Boolean = false

)
