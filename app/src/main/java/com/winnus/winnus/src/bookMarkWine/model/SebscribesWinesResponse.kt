package com.winnus.winnus.src.bookMarkWine.model

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class SebscribesWinesResponse(
    @SerializedName("result") val result: List<Result>
):BaseResponse()