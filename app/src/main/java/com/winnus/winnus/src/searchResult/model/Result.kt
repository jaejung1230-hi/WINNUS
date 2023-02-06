package com.winnus.winnus.src.searchResult.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("filteringRes") val filteringRes: List<List<FilteringRe>>
)