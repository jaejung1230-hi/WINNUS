package com.winnus.winnus.src.main.search.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("wineNames") val wineNames: List<WineName>
)