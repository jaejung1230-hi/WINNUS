package com.winnus.winnus.src.WineDetail.models

import com.google.gson.annotations.SerializedName


data class Reviews (
    @SerializedName("rating") val rating: Float,
    @SerializedName("content") val content: String,
    @SerializedName("reviewId") val reviewId: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("userName") val userName: String,
)