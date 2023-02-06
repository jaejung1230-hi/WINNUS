package com.winnus.winnus.src.searchResult.model

import com.google.gson.annotations.SerializedName

data class ResultX(
    @SerializedName("retrieveWineRes") val retrieveWineRes: List<RetrieveWineRes>,
    @SerializedName("wineCount") val wineCount: List<WineCount>
)