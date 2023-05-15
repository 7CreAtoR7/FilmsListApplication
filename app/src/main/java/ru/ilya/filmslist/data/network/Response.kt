package ru.ilya.filmslist.data.network

import com.google.gson.annotations.SerializedName

data class Response(

    @SerializedName("films")
    val films: List<FilmItemDTO>

)

