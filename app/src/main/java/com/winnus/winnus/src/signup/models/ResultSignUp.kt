package com.winnus.winnus.src.signup.models

import com.google.gson.annotations.SerializedName

data class ResultSignUp(
    @SerializedName("signUpUserid") val signUpUserid: Int,
)