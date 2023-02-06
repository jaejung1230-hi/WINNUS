package com.winnus.winnus.src.shopResult

import com.winnus.winnus.src.shopResult.models.ShopSearchResponse

interface ShopResultActivityView {

    fun onGetShopSuccess(response: ShopSearchResponse)
    fun onGetShopFailure(message: String)
}