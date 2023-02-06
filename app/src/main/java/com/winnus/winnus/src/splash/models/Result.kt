package com.winnus.winnus.src.splash.models

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("loginUserId") val loginUserId: Int
)