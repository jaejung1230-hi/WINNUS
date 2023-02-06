package com.winnus.winnus.src.ShopDetail.model

import com.google.gson.annotations.SerializedName

data class ResultX(
    @SerializedName("pairingWineList")  val pairingWineList: List<Wine>,
    @SerializedName("wineCount")  val wineCount: Int
)