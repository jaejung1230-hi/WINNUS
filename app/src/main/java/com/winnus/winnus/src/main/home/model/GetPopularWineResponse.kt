package com.winnus.winnus.src.main.home.model

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetPopularWineResponse(
    @SerializedName("result") val result : List<ResultPopularWine>
): BaseResponse()