package com.winnus.winnus.util

import com.google.gson.annotations.SerializedName

data class PostReportRequest(
    @SerializedName("reviewId") val reviewId: Int,
    @SerializedName("reasonId") val reasonId: Int,
)