package ru.ilya.filmslist.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.ilya.filmslist.domain.models.FilmItem

class FilmDiffCallback: DiffUtil.ItemCallback<FilmItem>() {
    override fun areItemsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
        return oldItem == newItem
    }
}