package ru.ilya.filmslist.domain.models

data class FilmItem(
    private val id: Long,
    private val name: String,
    private val year: String,
    private val genres: List<Genre>,
    private val posterUrl: String,
    private val isFavourite: Boolean = false
)
