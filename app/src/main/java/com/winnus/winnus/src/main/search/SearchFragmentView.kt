package com.winnus.winnus.src.main.search

import com.winnus.winnus.src.main.search.model.*

interface SearchFragmentView {

    fun onGetSearchWineNameSuccess(response: GetWineNameResponse)
    fun onGetSearchWineNameFailure(message: String)

    fun onPostSearchedSuccess(response: postSearchedResponse)
    fun onPostSearchedFailure(message: String)

    fun onGetSearchedSuccess(response: GetSearchedResponse)
    fun onGetSearchedFailure(message: String)

    fun onPatchSearchedSuccess(response: PatchSearchedResponse)
    fun onPatchSearchedFailure(message: String)

    fun onGetHotSearchedSuccess(response: HotSearchedResponse)
    fun onGetHotSearchedFailure(message: String)

    fun deleteSearched(i : Int)
    fun clickSearched(i : String)
}