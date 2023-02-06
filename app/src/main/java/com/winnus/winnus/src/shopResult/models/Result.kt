package com.winnus.winnus.src.shopResult.models

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("shopList") val shopList: List<Shop>,
    @SerializedName("wineShopCount") val wineShopCount: Int
)