package com.winnus.winnus.util

import com.google.gson.annotations.SerializedName

data class PostSubscribeRequest(
    @SerializedName("wineId") val wineId: Int
)