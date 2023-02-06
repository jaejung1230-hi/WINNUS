package com.winnus.winnus.src.main.home

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.main.home.model.GetThemeWineResponse


interface ThemeWineFragmentView {

    fun onGetThemeWineSuccess(response: GetThemeWineResponse)
    fun onGetThemeWineFailure(message: String)

    fun onPostSubscribeSuccess(response: BaseResponse)
    fun onPostSubscribeFailure(message: String)

    fun tryPostSubscribe(wineId : Int)
}