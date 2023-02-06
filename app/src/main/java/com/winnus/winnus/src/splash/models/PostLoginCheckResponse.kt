package com.winnus.winnus.src.splash.models

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class PostLoginCheckResponse(
    @SerializedName("result") val result: Result
): BaseResponse()