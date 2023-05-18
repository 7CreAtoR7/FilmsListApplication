package ru.ilya.filmslist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import ru.ilya.filmslist.R
import ru.ilya.filmslist.databinding.CardWithStarBinding
import ru.ilya.filmslist.databinding.CardWithoutStarBinding
import ru.ilya.filmslist.domain.models.FilmItem

class FilmsAdapter : ListAdapter<FilmItem, FilmViewHolder>(FilmDiffCallback()) {

    var onFilmItemLongClickListener: ((FilmItem) -> Unit)? = null
    var onFilmItemClickListener: ((FilmItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layout = when (viewType) {
            // устанавливаем макет для избранного/обычного фильма
            VIEW_TYPE_STAR -> R.layout.card_with_star
            VIEW_TYPE_WITHOUT_STAR -> R.layout.card_without_star
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        val binding = holder.binding

        binding.root.setOnLongClickListener {
            onFilmItemLongClickListener?.invoke(film)
            true
        }

        binding.root.setOnClickListener {
            onFilmItemClickListener?.invoke(film)
        }

        when (binding) {
            is CardWithStarBinding -> {
                Glide.with(holder.itemView.context).load(film.posterUrl).into(binding.moviePoster)
                val genre = film.genres.map { it.genre.replaceFirstChar(Char::titlecase) }[FIRST_GENRE]
                val year = film.year
                binding.movieReleaseDate.text = String.format("%s (%s)", genre, year)
                binding.movieTitle.text = film.name
            }
            is CardWithoutStarBinding -> {
                Glide.with(holder.itemView.context).load(film.posterUrl).into(binding.moviePoster)
                val genre = film.genres.map { it.genre.replaceFirstChar(Char::titlecase) }[FIRST_GENRE]
                val year = film.year
                binding.movieReleaseDate.text = String.format("%s (%s)", genre, year)
                binding.movieTitle.text = film.name
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.isFavourite)
            VIEW_TYPE_STAR
        else
            VIEW_TYPE_WITHOUT_STAR
    }

    companion object {
        const val FIRST_GENRE = 0
        const val VIEW_TYPE_STAR = 100
        const val VIEW_TYPE_WITHOUT_STAR = 101
        const val MAX_POOL_SIZE = 15
    }
}
