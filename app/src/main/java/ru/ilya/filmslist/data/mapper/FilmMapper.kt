package ru.ilya.filmslist.data.mapper

import ru.ilya.filmslist.data.database.entities.FilmItemDBModel
import ru.ilya.filmslist.data.network.DetailedFilmDTO
import ru.ilya.filmslist.data.network.FilmItemDTO
import ru.ilya.filmslist.domain.models.DetailedFilmItem
import ru.ilya.filmslist.domain.models.FilmItem
import javax.inject.Inject

class FilmMapper @Inject constructor() {

    private fun mapFilmItemDTOToFilmItem(filmItemDTO: FilmItemDTO): FilmItem =
        FilmItem(
            name = filmItemDTO.name ?: DEFAULT_NAME,
            year = filmItemDTO.year ?: DEFAULT_YEAR,
            genres = filmItemDTO.genres ?: emptyList(),
            countries = filmItemDTO.countries ?: emptyList(),
            posterUrl = filmItemDTO.posterUrl ?: DEFAULT_POSTER_URL,
            isFavourite = filmItemDTO.isFavourite,
            id = filmItemDTO.id ?: DEFAULT_ID,
        )

    fun mapListFilmItemDTOToListFilmItem(listFilmItemDTO: List<FilmItemDTO>): List<FilmItem> =
        listFilmItemDTO.map { mapFilmItemDTOToFilmItem(it) }

    fun mapDetailedFilmDTOToDetailedFilmItem(detailedFilmDTO: DetailedFilmDTO): DetailedFilmItem =
        DetailedFilmItem(
            id = detailedFilmDTO.id ?: DEFAULT_ID,
            name = detailedFilmDTO.nameRu ?: DEFAULT_NAME,
            posterURL = detailedFilmDTO.posterURL ?: DEFAULT_POSTER_URL,
            description = detailedFilmDTO.description ?: DEFAULT_DESCRIPTION,
            countries = detailedFilmDTO.countries ?: emptyList(),
            genres = detailedFilmDTO.genres ?: emptyList()
        )

    private fun mapFilmItemToFilmItemDBModel(filmItem: FilmItem): FilmItemDBModel =
        FilmItemDBModel(
            id = filmItem.id,
            name = filmItem.name,
            year = filmItem.year,
            countries = filmItem.countries,
            genres = filmItem.genres,
            posterUrl = filmItem.posterUrl,
            is_favourite = filmItem.isFavourite
        )

    fun mapListFilmItemToListFilmItemDBModel(listFilmItem: List<FilmItem>): List<FilmItemDBModel> =
        listFilmItem.map { mapFilmItemToFilmItemDBModel(it) }

    private fun mapFilmItemDBModelToFilmItem(filmItemDBModel: FilmItemDBModel): FilmItem =
        FilmItem(
            name = filmItemDBModel.name,
            year = filmItemDBModel.year,
            genres = filmItemDBModel.genres ?: emptyList(),
            countries = filmItemDBModel.countries ?: emptyList(),
            posterUrl = filmItemDBModel.posterUrl,
            isFavourite = filmItemDBModel.is_favourite,
            id = filmItemDBModel.id,
        )

    fun mapListFilmItemDBModelToListFilmItem(listFilmItemDBModel: List<FilmItemDBModel>): List<FilmItem> =
        listFilmItemDBModel.map { mapFilmItemDBModelToFilmItem(it) }

    companion object {

        private const val DEFAULT_NAME = ""
        private const val DEFAULT_YEAR = ""
        private const val DEFAULT_POSTER_URL = ""
        private const val DEFAULT_DESCRIPTION = ""
        private const val DEFAULT_ID = -1L
    }
}