package com.winnus.winnus.src.main.home.model

import com.google.gson.annotations.SerializedName

data class ResultPopularWine (
    @SerializedName("wineId") val wineId : Int,
    @SerializedName("wineImg") val wineImg : String,
    @SerializedName("wineName") val wineName : String,
    @SerializedName("price") val price : String,
    @SerializedName("userSubscribeStatus") var userSubscribeStatus : String
        )