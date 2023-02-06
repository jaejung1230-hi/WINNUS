package com.winnus.winnus.src.login.models

import com.google.gson.annotations.SerializedName

data class GetSignInRequest(
    @SerializedName("phoneNum") val phoneNum: String,
    @SerializedName("pwd") val pwd: String,
)