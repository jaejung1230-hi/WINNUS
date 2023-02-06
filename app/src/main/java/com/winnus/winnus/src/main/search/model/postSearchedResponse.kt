package com.winnus.winnus.src.main.search.model

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class postSearchedResponse(
    @SerializedName("result") val result: ResultX
):BaseResponse()