package com.winnus.winnus.src.main.search.model

import com.google.gson.annotations.SerializedName

data class ResultXX(
    @SerializedName("searchedList") val searchedList: ArrayList<Searched>
)