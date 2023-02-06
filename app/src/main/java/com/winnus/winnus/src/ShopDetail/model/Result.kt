package com.winnus.winnus.src.ShopDetail.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("pairingFoodList") val pairingFoodList: ArrayList<PairingFood>,
    @SerializedName("wineCount") val wineCount: Int,
    @SerializedName("wineList") val wineList: List<Wine>
)