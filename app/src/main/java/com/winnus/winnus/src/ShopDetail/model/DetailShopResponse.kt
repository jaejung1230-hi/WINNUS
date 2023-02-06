package com.winnus.winnus.src.ShopDetail.model

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class DetailShopResponse(
    @SerializedName("result") val result: List<Result>
): BaseResponse()