package com.winnus.winnus.src.ReWriteReview.model

import com.google.gson.annotations.SerializedName

data class PatchReviewRequest(
    @SerializedName("rating") val rating: Float,
    @SerializedName("content") val content: String,
    @SerializedName("tagList") val tagList: ArrayList<Map<String,String>>?
)