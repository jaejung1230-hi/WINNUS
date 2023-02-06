package com.winnus.winnus.src.main.search.model

import com.google.gson.annotations.SerializedName

data class Searched(
    @SerializedName("keyword") val keyword: String,
    @SerializedName("searchId") val searchId: Int
)