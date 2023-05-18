package ru.ilya.filmslist.presentation.ui.filmsScreen

import ru.ilya.filmslist.domain.models.FilmItem

sealed class FilmsListState {
    data class Success(val filmsList: List<FilmItem>) : FilmsListState()
    data class Error(val message: String) : FilmsListState()
    object Loading : FilmsListState()
}