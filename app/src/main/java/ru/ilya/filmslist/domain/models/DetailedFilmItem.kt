package ru.ilya.filmslist.domain.models

data class DetailedFilmItem(
    val id: Long,
    val name: String,
    val posterURL: String,
    val description: String,
    val countries: List<Country>,
    val genres: List<Genre>,
)
