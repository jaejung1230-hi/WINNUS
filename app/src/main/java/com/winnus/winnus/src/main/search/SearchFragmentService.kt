package com.winnus.winnus.src.main.search

import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.src.main.search.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragmentService(var view: SearchFragmentView) {
    fun tryGetSearchWineName(){
        val searchFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SearchFragmentInterface::class.java)
        searchFragmentInterface.getThemeWine().enqueue(object : Callback<GetWineNameResponse>{
            override fun onResponse(call: Call<GetWineNameResponse>, response: Response<GetWineNameResponse>) {
                view.onGetSearchWineNameSuccess(response.body() as GetWineNameResponse)
            }

            override fun onFailure(call: Call<GetWineNameResponse>, t: Throwable) {
                view.onGetSearchWineNameFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSearched(postSearchedRequest : PostSearchedRequest){
        val searchFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SearchFragmentInterface::class.java)
        searchFragmentInterface.postSearched(postSearchedRequest).enqueue(object : Callback<postSearchedResponse>{
            override fun onResponse(call: Call<postSearchedResponse>, response: Response<postSearchedResponse>) {
                view.onPostSearchedSuccess(response.body() as postSearchedResponse)
            }

            override fun onFailure(call: Call<postSearchedResponse>, t: Throwable) {
                view.onPostSearchedFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetSearched(){
        val searchFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SearchFragmentInterface::class.java)
        searchFragmentInterface.getSearched().enqueue(object : Callback<GetSearchedResponse>{
            override fun onResponse(call: Call<GetSearchedResponse>, response: Response<GetSearchedResponse>) {
                view.onGetSearchedSuccess(response.body() as GetSearchedResponse)
            }

            override fun onFailure(call: Call<GetSearchedResponse>, t: Throwable) {
                view.onGetSearchedFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetHotSearched(){
        val searchFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SearchFragmentInterface::class.java)
        searchFragmentInterface.getHotSearched().enqueue(object : Callback<HotSearchedResponse>{
            override fun onResponse(call: Call<HotSearchedResponse>, response: Response<HotSearchedResponse>) {
                view.onGetHotSearchedSuccess(response.body() as HotSearchedResponse)
            }

            override fun onFailure(call: Call<HotSearchedResponse>, t: Throwable) {
                view.onGetHotSearchedFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchSearched(searchId : Int?){
        val searchFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SearchFragmentInterface::class.java)
        searchFragmentInterface.patchSearched(searchId).enqueue(object : Callback<PatchSearchedResponse>{
            override fun onResponse(call: Call<PatchSearchedResponse>, response: Response<PatchSearchedResponse>) {
                view.onPatchSearchedSuccess(response.body() as PatchSearchedResponse)
            }

            override fun onFailure(call: Call<PatchSearchedResponse>, t: Throwable) {
                view.onPatchSearchedFailure(t.message ?: "통신 오류")
            }
        })
    }
}