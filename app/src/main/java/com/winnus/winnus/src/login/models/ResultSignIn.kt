package com.winnus.winnus.src.login.models

import com.google.gson.annotations.SerializedName

data class ResultSignIn(
    @SerializedName("userId") val userId: Int,
    @SerializedName("jwt") val jwt: String
)