package com.mypro.moviedb.ui.movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mypro.moviedb.data.model.Movie
import com.mypro.moviedb.data.model.Review
import com.mypro.moviedb.databinding.ItemMovieBinding
import com.mypro.moviedb.databinding.ItemReviewBinding

class ReviewAdapter : PagingDataAdapter<Review, ReviewAdapter.ReviewViewHolder>(DiffCallback) {

    inner class ReviewViewHolder(
        var binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review?) {
            binding.review = review
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Review>() {

        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ReviewViewHolder(
            ItemReviewBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

}