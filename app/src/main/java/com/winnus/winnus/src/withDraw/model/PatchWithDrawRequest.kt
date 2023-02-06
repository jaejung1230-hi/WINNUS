package com.winnus.winnus.src.withDraw.model

import com.google.gson.annotations.SerializedName

data class PatchWithDrawRequest(
    @SerializedName("pwd") val pwd: String,
    @SerializedName("reasonId") val reasonId: Int,
)