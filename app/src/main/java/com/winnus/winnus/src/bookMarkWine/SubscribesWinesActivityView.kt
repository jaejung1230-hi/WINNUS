package com.winnus.winnus.src.bookMarkWine

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.bookMarkWine.model.SebscribesWinesResponse

interface SubscribesWinesActivityView {
    fun onGetAllReviewSuccess(response: SebscribesWinesResponse)
    fun onGetAllReviewFailure(message: String)

    fun onPostSubscribeSuccess(response: BaseResponse)
    fun onPostSubscribeFailure(message: String)

    fun tryPostSubscribe(wineId : Int)
}