package com.winnus.winnus.src.main.home.model

import com.google.gson.annotations.SerializedName

data class ResultTodayWine (
    @SerializedName("wineId") val wineId : Int,
    @SerializedName("wineImg") val wineImg : String,
    @SerializedName("wineName") val wineName : String,
    @SerializedName("price") val price : String,
    @SerializedName("country") val country : String,
    @SerializedName("region") val region : String,
    @SerializedName("userSubscribeStatus") var userSubscribeStatus : String
        )