package com.winnus.winnus.src.main.search.model

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetSearchedResponse(
    @SerializedName("result") val result: ResultXX
): BaseResponse()