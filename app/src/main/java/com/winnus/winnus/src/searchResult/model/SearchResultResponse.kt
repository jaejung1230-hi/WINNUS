package com.winnus.winnus.src.searchResult.model

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class SearchResultResponse(
    @SerializedName("result") val result: List<ResultX>
):BaseResponse()