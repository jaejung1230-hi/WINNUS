package com.winnus.winnus.src.searchResult

import android.util.Log
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.WineDetail.DetailWineActivity
import com.winnus.winnus.src.searchResult.model.SearchFilterResponse
import com.winnus.winnus.src.searchResult.model.SearchResultResponse
import com.winnus.winnus.util.PostSubscribeRequest
import com.winnus.winnus.util.SearchFilterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultActivityService(private val view: SearchResultActivityView) {
    fun tryGetSearchResult(text : String){
        val searchResultActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SearchResultActivityInterface::class.java)
        searchResultActivityInterface.getSearchResult(text).enqueue(object : Callback<SearchResultResponse> {
            override fun onResponse(call: Call<SearchResultResponse>, response: Response<SearchResultResponse>) {
                Log.d("SearchResultActivity",response.toString())
                view.onGetSearchResultSuccess(response.body() as SearchResultResponse)
            }

            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                Log.d("SearchResultActivity",t.toString())
                view.onGetSearchResultFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetFilterResult(searchFilterRequest : SearchFilterRequest, pageNum : Int, orderBy : String){
        var type : String? = null
        var flavors : String? = null
        var foods : String? = null
        val searchResultActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SearchResultActivityInterface::class.java)
        if(searchFilterRequest.type != null){
            type = searchFilterRequest.type.toString().replace("[","").replace("]","").replace(" ","")
            Log.d("SearchResultActivity",type)
        }

        if(searchFilterRequest.flavors != null){
            flavors = searchFilterRequest.flavors.toString().replace("[","").replace("]","").replace(" ","")
        }
        if(searchFilterRequest.foods != null){
            foods = searchFilterRequest.foods.toString().replace("[","").replace("]","").replace(" ","")
        }
        searchResultActivityInterface.getFilterResult(searchFilterRequest.keyword,type,searchFilterRequest.sweetness,searchFilterRequest.acidity,searchFilterRequest.body,searchFilterRequest.tannin,flavors,
            foods,searchFilterRequest.price, pageNum, orderBy).enqueue(object : Callback<SearchFilterResponse> {
            override fun onResponse(call: Call<SearchFilterResponse>, response: Response<SearchFilterResponse>) {
                Log.d("SearchResultActivity",response.toString())
                view.onGetFilterResultSuccess(response.body() as SearchFilterResponse)
            }

            override fun onFailure(call: Call<SearchFilterResponse>, t: Throwable) {
                Log.d("SearchResultActivity",t.toString())
                view.onGetFilterResultFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSubscribe(postSubscribeRequest : PostSubscribeRequest){
        val searchResultActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SearchResultActivityInterface::class.java)
        searchResultActivityInterface.postSubscribe(postSubscribeRequest).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("tryPostSubscribe",response.toString())
                view.onPostSubscribeSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostSubscribeFailure(t.message ?: "통신 오류")
            }
        })
    }
}