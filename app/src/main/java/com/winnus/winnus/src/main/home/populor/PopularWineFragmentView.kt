package com.winnus.winnus.src.main.home.populor

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.main.home.model.GetPopularWineResponse

interface PopularWineFragmentView {
    fun onGetPopularWineSuccess(response: GetPopularWineResponse)
    fun onGetPopularWineFailure(message: String)

    fun onPostSubscribeSuccess(response: BaseResponse)
    fun onPostSubscribeFailure(message: String)

    fun tryPostSubscribe(wineId : Int)
}