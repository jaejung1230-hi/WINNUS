package com.winnus.winnus.src.signup.models

import com.google.gson.annotations.SerializedName

data class GetCodeRequest(
    @SerializedName("phoneNum") val phoneNum: String,
)