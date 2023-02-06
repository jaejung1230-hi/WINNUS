package com.winnus.winnus.src.signup.models

import com.google.gson.annotations.SerializedName

data class GetSignUpRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("phoneNum") val phoneNum: String,
    @SerializedName("pwd") val pwd: String,
)