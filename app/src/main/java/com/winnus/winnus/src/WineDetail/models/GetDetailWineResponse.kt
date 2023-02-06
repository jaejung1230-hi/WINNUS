package com.winnus.winnus.src.WineDetail.models

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.login.models.ResultSignIn
import com.google.gson.annotations.SerializedName

data class GetDetailWineResponse(
    @SerializedName("result") val result: List<ResultDetailWine>
): BaseResponse()