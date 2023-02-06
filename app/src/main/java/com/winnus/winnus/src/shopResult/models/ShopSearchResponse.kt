package com.winnus.winnus.src.shopResult.models

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ShopSearchResponse(
    @SerializedName("result")  val result: List<Result>
):BaseResponse()