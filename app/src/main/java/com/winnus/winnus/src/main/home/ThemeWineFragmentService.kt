package com.winnus.winnus.src.main.home

import android.util.Log
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.main.home.model.GetThemeWineResponse
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThemeWineFragmentService(var view: ThemeWineFragmentView) {


    fun tryGetThemeWine(theme : String){
        val themeWineFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(ThemeWineFragmentInterface::class.java)
        themeWineFragmentInterface.getThemeWine(theme).enqueue(object : Callback<GetThemeWineResponse>{
            override fun onResponse(call: Call<GetThemeWineResponse>, response: Response<GetThemeWineResponse>) {
                view.onGetThemeWineSuccess(response.body() as GetThemeWineResponse)
            }

            override fun onFailure(call: Call<GetThemeWineResponse>, t: Throwable) {
                view.onGetThemeWineFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSubscribe(postSubscribeRequest : PostSubscribeRequest){
        val themeWineFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(ThemeWineFragmentInterface::class.java)
        themeWineFragmentInterface.postSubscribe(postSubscribeRequest).enqueue(object : Callback<BaseResponse>{
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