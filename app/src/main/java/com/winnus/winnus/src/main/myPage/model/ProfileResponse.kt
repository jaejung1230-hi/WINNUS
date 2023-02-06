package com.winnus.winnus.src.main.myPage.model

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("result") val result: List<Result>
) : BaseResponse()