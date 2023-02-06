package com.winnus.winnus.src.main.search.model

import com.google.gson.annotations.SerializedName

data class HotSearched(
    @SerializedName("keyword") val keyword: String
)