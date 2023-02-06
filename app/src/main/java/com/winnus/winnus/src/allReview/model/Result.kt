package com.winnus.winnus.src.allReview.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("wineReviews") val wineReviews: List<WineReview>
)