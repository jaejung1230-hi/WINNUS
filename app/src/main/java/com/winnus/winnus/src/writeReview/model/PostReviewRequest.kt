package com.winnus.winnus.src.writeReview.model

import com.google.gson.annotations.SerializedName

data class PostReviewRequest(
    @SerializedName("wineId") val wineId: Int,
    @SerializedName("rating") val rating: Float,
    @SerializedName("content") val content: String,
    @SerializedName("tagList") val tagList: ArrayList<Map<String,String>>?
)