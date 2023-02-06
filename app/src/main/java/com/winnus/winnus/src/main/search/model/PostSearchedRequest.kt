package com.winnus.winnus.src.main.search.model

import com.google.gson.annotations.SerializedName

data class PostSearchedRequest(
    @SerializedName("keyword") val keyword: String
)