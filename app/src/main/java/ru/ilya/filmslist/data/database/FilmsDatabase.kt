package ru.ilya.filmslist.data.database


import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.ilya.filmslist.data.database.converters.ConverterCountry
import ru.ilya.filmslist.data.database.converters.ConverterGenre
import ru.ilya.filmslist.data.database.entities.FilmItemDBModel

@Database(
    entities = [FilmItemDBModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ConverterCountry::class, ConverterGenre::class)
abstract class FilmsDatabase : RoomDatabase() {

    abstract fun filmsDao(): FilmsDao

    companion object {
        private var INSTANCE: FilmsDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "films_db.db"

        fun getInstance(application: Application): FilmsDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    FilmsDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}