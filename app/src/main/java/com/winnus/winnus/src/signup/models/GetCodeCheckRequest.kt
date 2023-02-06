package com.winnus.winnus.src.signup.models

import com.google.gson.annotations.SerializedName

data class GetCodeCheckRequest(
    @SerializedName("phoneNum") val phoneNum: String,
    @SerializedName("verifyNum") val verifyNum: Int,
)