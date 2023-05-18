package ru.ilya.filmslist.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.ilya.filmslist.data.database.entities.FilmItemDBModel

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films")
    fun getAllFilmsListFromDb(): LiveData<List<FilmItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFilmsListToDb(filmsList: List<FilmItemDBModel>)

    @Query("SELECT * FROM films WHERE is_favourite=1")
    fun getFavouriteFilmsFromDb(): LiveData<List<FilmItemDBModel>>

    @Query("UPDATE films SET is_favourite = :isFavourite WHERE id = :id")
    suspend fun updateFilmFavouriteStatus(isFavourite: Boolean, id: Long)

}