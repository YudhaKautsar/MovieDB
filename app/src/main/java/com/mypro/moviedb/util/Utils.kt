package com.mypro.moviedb.util

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.mypro.moviedb.R
import com.mypro.moviedb.data.model.ErrorResponse
import retrofit2.HttpException

object Utils {
    fun showSnackBar(
        view: View,
        message: String,
        type: Int,
        duration: Int = Snackbar.LENGTH_SHORT,
        anchor: View? = null,
    ) {
        val snackBar = Snackbar.make(view, message, duration)
        val tv =
            snackBar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        when (type) {
            SnackBarType.SUCCESS -> {
                snackBar.view.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.green
                    )
                )
                tv.setTextColor(ContextCompat.getColor(view.context, R.color.white))
            }
            SnackBarType.ERROR -> {
                snackBar.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
                tv.setTextColor(ContextCompat.getColor(view.context, R.color.white))
            }
        }
        if (anchor != null)
            snackBar.anchorView = anchor
        snackBar.show()
    }

    fun View.visible(){
        this.visibility = View.VISIBLE
    }

    fun View.gone(){
        this.visibility = View.GONE
    }

    fun Exception.errorMessage() = if (this is HttpException) Gson().fromJson(
        this.response()?.errorBody()?.string(),
        ErrorResponse::class.java
    ).message else this.message

}