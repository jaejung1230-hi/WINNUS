package com.winnus.winnus.src.searchResult.model

import com.google.gson.annotations.SerializedName

data class FilteringReX(
    @SerializedName("country") val country: String,
    @SerializedName("price") val price: String,
    @SerializedName("region") val region: String,
    @SerializedName("quantity") val quantity: String,
    @SerializedName("reviewCount") val reviewCount: Int,
    @SerializedName("subscribeCount") val subscribeCount: Int,
    @SerializedName("userSubscribeStatus") var userSubscribeStatus: String,
    @SerializedName("wineId") val wineId: Int,
    @SerializedName("wineImg") val wineImg: String,
    @SerializedName("wineName") val wineName: String
)