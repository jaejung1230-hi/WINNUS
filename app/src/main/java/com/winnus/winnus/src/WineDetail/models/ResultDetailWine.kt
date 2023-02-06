package com.winnus.winnus.src.WineDetail.models

import com.google.gson.annotations.SerializedName

data class ResultDetailWine(
    @SerializedName("wineInfo") val wineInfo: List<WineInfo>,
    @SerializedName("flavorList") val flavorList: List<FlavorList>,
    @SerializedName("pairingFoodList") val pairingFoodList: List<PairingFoodList>,
    @SerializedName("reviews") val reviews: List<Reviews>,
    @SerializedName("bestWineListByType") val bestWineListByType: List<BestWineListByType>,
    @SerializedName("similarWineList") val similarWineList: List<SimilarWineList>,
    )