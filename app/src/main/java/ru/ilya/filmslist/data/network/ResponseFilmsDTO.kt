package ru.ilya.filmslist.data.network

import com.google.gson.annotations.SerializedName

data class ResponseFilmsDTO(

    @SerializedName("films")
    val films: List<FilmItemDTO>

)

