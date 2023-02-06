package com.winnus.winnus.src.bookMarkWine.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("subscribeList") val subscribeList: List<Subscribe>,
    @SerializedName("subscribeNum") val subscribeNum: Int
)