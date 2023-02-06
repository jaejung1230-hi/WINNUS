package com.winnus.winnus.src.myReview.model

import com.google.gson.annotations.SerializedName

data class Result(

    @SerializedName("reviewNum") val reviewNum: Int,
    @SerializedName("userReviews") val userReviews: List<UserReview>
)