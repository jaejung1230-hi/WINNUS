package com.winnus.winnus.src.WineDetail.models

import com.google.gson.annotations.SerializedName

data class FlavorList (
    @SerializedName("flavor") val flavor: String,
    @SerializedName("flavorImg") val flavorImg: String
        )