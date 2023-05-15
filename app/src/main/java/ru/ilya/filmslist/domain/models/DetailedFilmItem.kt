package ru.ilya.filmslist.domain.models

data class DetailedFilmItem(
    private val id: Long,

    private val name: String,

    private val posterURL: String,

    private val description: String,

    private val countries: List<Country>,

    private val genres: List<Genre>,
)
