package ru.ilya.filmslist.domain.models

data class FilmItem(
    val name: String,
    val year: String,
    val genres: List<Genre>,
    val countries: List<Country>,
    val posterUrl: String,
    val isFavourite: Boolean,
    val id: Long = UNDEFINED_ID,
) {
    companion object {
        const val UNDEFINED_ID = 0L
    }
}
