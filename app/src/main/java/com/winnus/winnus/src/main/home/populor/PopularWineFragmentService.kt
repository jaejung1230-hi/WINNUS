package com.winnus.winnus.src.main.home.populor

import android.util.Log
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.main.home.model.GetPopularWineResponse
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularWineFragmentService(var view: PopularWineFragmentView) {

    fun tryGetPopularWine(type : Int?){
        val popularWineFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(PopularWineFragmentInterface::class.java)
        popularWineFragmentInterface.getPopularWine(type).enqueue(object : Callback<GetPopularWineResponse>{
            override fun onResponse(call: Call<GetPopularWineResponse>, response: Response<GetPopularWineResponse>) {
                view.onGetPopularWineSuccess(response.body() as GetPopularWineResponse)
            }

            override fun onFailure(call: Call<GetPopularWineResponse>, t: Throwable) {
                view.onGetPopularWineFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSubscribe(postSubscribeRequest : PostSubscribeRequest){
        val popularWineFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(PopularWineFragmentInterface::class.java)
        popularWineFragmentInterface.postSubscribe(postSubscribeRequest).enqueue(object : Callback<BaseResponse>{
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