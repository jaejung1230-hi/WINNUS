package com.winnus.winnus.src.allReview.model

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("content") val content: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("reviewId") val reviewId: Int,
    @SerializedName("userName")val userName: String,
    )