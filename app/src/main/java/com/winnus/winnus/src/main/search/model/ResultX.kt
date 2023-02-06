package com.winnus.winnus.src.main.search.model

import com.google.gson.annotations.SerializedName

data class ResultX(
    @SerializedName("insertSearchKeywordId") val insertSearchKeywordId: Int,
    @SerializedName("updatedSearchKeywordId") val updatedSearchKeywordId: Int
)