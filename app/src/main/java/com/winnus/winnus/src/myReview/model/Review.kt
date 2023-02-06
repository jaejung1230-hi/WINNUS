package com.winnus.winnus.src.myReview.model

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("content") val content: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("reviewId") val reviewId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("wineId") val wineId: Int,
    @SerializedName("wineName") val wineName: String,
    @SerializedName("wineImg") val wineImg: String,
    @SerializedName("country") val country: String,
    @SerializedName("region") val region: String
)