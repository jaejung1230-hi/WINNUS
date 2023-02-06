package com.winnus.winnus.src.withDraw

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.shopResult.models.ShopSearchResponse

interface WithDrawActivityView {
    fun onWithDrawSuccess(response: BaseResponse)
    fun onWithDrawFailure(message: String)
}