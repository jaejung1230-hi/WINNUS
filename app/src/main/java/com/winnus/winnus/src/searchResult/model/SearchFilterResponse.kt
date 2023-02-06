package com.winnus.winnus.src.searchResult.model

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class SearchFilterResponse(
    @SerializedName("result") val result: Result
):BaseResponse()