package com.winnus.winnus.src.main.home.model

import com.google.gson.annotations.SerializedName

data class ThemeWineList (
    @SerializedName("themeWineList") val themeWineList : List<ResultTodayWine>
        )