package com.winnus.winnus.src.WineDetail.models

import com.google.gson.annotations.SerializedName

data class PairingFoodList (
    @SerializedName("food") val food: String,
    @SerializedName("foodImg") val foodImg: String
        )