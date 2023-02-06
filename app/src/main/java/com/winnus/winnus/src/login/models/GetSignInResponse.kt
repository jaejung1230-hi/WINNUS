package com.winnus.winnus.src.login.models

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetSignInResponse(
    @SerializedName("result") val result: ResultSignIn
): BaseResponse()