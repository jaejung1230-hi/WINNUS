package com.winnus.winnus.src.allReview.model

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class AllReviewResponse(
    @SerializedName("result") val result: Result
):BaseResponse()