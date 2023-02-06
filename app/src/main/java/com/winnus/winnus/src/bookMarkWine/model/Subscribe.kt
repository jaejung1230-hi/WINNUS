package com.winnus.winnus.src.bookMarkWine.model

import com.google.gson.annotations.SerializedName

data class Subscribe(
    @SerializedName("country") val country: String,
    @SerializedName("price")val price: String,
    @SerializedName("quantity")val quantity: String,
    @SerializedName("region")val region: String,
    @SerializedName("reviewCount")val reviewCount: Int,
    @SerializedName("subscribeCount")val subscribeCount: Int,
    @SerializedName("wineId")val wineId: Int,
    @SerializedName("wineImg")val wineImg: String,
    @SerializedName("wineName")val wineName: String,
    @SerializedName("userSubscribeStatus")var userSubscribeStatus: String
)