package com.winnus.winnus.src.ShopDetail

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.ShopDetail.model.DetailShopResponse
import com.winnus.winnus.src.ShopDetail.model.ShopPairingResponse

interface DetailShopActivityView {
    fun onGetShopDetailSuccess(response : DetailShopResponse)
    fun onGetShopDetailFailure(message: String)

    fun onPostSubscribeSuccess(response: BaseResponse)
    fun onPostSubscribeFailure(message: String)

    fun onGetShopPairingSuccess(response : ShopPairingResponse)
    fun onGetShopPairingFailure(message: String)

    fun tryPostSubscribe(wineId : Int)

    fun tryGetShopPairing(wineId : Int)
}