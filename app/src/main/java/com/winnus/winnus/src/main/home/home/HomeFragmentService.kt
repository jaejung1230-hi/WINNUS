package com.winnus.winnus.src.main.home.home

import android.util.Log
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.main.home.model.GetTodayWineResponse
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentService(var view: HomeFragmentView) {


    fun tryGetTodayWine(){
        val homeFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(HomeFragmentInterface::class.java)
        homeFragmentInterface.getTodayWine().enqueue(object : Callback<GetTodayWineResponse>{
            override fun onResponse(call: Call<GetTodayWineResponse>, response: Response<GetTodayWineResponse>) {
                view.onGetTodayWineSuccess(response.body() as GetTodayWineResponse)
            }

            override fun onFailure(call: Call<GetTodayWineResponse>, t: Throwable) {
                view.onGetTodayWineFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSubscribe(postSubscribeRequest : PostSubscribeRequest){
        val homeFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(
            HomeFragmentInterface::class.java)
        homeFragmentInterface.postSubscribe(postSubscribeRequest).enqueue(object : Callback<BaseResponse>{
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