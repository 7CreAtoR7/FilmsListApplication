package ru.ilya.filmslist.presentation.ui.detailedScreen

import ru.ilya.filmslist.domain.models.DetailedFilmItem


sealed class DetailedFilmState {
    data class Success(val detailedFilmItem: DetailedFilmItem) : DetailedFilmState()
    data class Error(val message: String) : DetailedFilmState()
    object Loading : DetailedFilmState()
}