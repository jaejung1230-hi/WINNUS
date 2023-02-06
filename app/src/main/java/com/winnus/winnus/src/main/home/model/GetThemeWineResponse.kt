package com.winnus.winnus.src.main.home.model

import com.winnus.winnus.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetThemeWineResponse(
    @SerializedName("result") val result : ThemeWineList
): BaseResponse()