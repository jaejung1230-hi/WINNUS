package com.winnus.winnus.src.main.home.model

import com.google.gson.annotations.SerializedName

data class TodayWines (
    @SerializedName("todayWines") val todayWines : List<ResultTodayWine>
        )