package com.winnus.winnus.src.main.home.home

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.main.home.model.GetTodayWineResponse

interface HomeFragmentView {

    fun onGetTodayWineSuccess(response: GetTodayWineResponse)
    fun onGetTodayWineFailure(message: String)

    fun onPostSubscribeSuccess(response: BaseResponse)
    fun onPostSubscribeFailure(message: String)

    fun tryPostSubscribe(wineId : Int)
}