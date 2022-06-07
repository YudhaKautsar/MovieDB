package com.mypro.moviedb.ui.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mypro.moviedb.data.model.Genre
import com.mypro.moviedb.databinding.ItemGenreBinding

class GenreAdapter : ListAdapter<Genre, GenreAdapter.GenreViewHolder>(DiffCallback) {
    var onClick: ((Genre) -> Unit)? = null

    inner class GenreViewHolder(
        var binding: ItemGenreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.genre = genre
            binding.root.setOnClickListener {
                onClick?.invoke(genre)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Genre>() {

        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.name == newItem.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GenreViewHolder(
            ItemGenreBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre)
    }

}