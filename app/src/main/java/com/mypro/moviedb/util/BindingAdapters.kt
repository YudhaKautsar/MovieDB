package com.mypro.moviedb.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mypro.moviedb.util.Constants.BASE_IMAGE_URL

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (url?.isNotEmpty() == true)
        Glide.with(view.context).load(BASE_IMAGE_URL + url).into(view)
}