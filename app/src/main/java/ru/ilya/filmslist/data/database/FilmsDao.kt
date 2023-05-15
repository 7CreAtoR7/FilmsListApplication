package ru.ilya.filmslist.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.ilya.filmslist.data.database.entities.FilmItemDBModel

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films")
    fun getAllFilmsListFromDb(): LiveData<List<FilmItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilmsListToDb(filmsList: List<FilmItemDBModel>)

    @Query("SELECT * FROM films WHERE favourite=1")
    fun getFavouriteFilmsFromDb(): LiveData<List<FilmItemDBModel>>

    @Query("UPDATE film_item SET isLove = :isFavourite WHERE id = :id")
    suspend fun updateFilmFavouriteStatus(filmItemDBModel: FilmItemDBModel)

}