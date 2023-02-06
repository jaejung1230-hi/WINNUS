package com.winnus.winnus.src.searchResult

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.searchResult.model.SearchFilterResponse
import com.winnus.winnus.src.searchResult.model.SearchResultResponse

interface SearchResultActivityView {
    fun onGetSearchResultSuccess(response: SearchResultResponse)
    fun onGetSearchResultFailure(message: String)

    fun onGetFilterResultSuccess(response: SearchFilterResponse)
    fun onGetFilterResultFailure(message: String)

    fun onPostSubscribeSuccess(response: BaseResponse)
    fun onPostSubscribeFailure(message: String)

    fun tryPostSubscribe(wineId : Int)
}