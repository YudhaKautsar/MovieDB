package com.mypro.moviedb.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status_message")
    val message: String?
)