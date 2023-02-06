package com.winnus.winnus.src.signup.models

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetSignUpResponse(
    @SerializedName("result") val result: ResultSignUp
): BaseResponse()