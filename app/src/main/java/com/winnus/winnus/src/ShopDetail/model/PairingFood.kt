package com.winnus.winnus.src.ShopDetail.model

import com.google.gson.annotations.SerializedName

data class PairingFood(
    @SerializedName("food") val food: String,
    @SerializedName("foodCategoryId") val foodCategoryId: Int,
    @SerializedName("foodImg") val foodImg: String
)