package com.winnus.winnus.src.myReview.model

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class MyReviewResponse(
    @SerializedName("result") val result: List<Result>
):BaseResponse()