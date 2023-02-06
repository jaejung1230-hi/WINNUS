package com.winnus.winnus.src.WineDetail.models

import com.google.gson.annotations.SerializedName

data class BestWineListByType (
    @SerializedName("wineId") val wineId: Int,
    @SerializedName("wineImg") val wineImg: String,
    @SerializedName("wineName") val wineName: String,
    @SerializedName("price") val price: String,
    @SerializedName("country") val country: String,
    @SerializedName("region") val region: String
        )