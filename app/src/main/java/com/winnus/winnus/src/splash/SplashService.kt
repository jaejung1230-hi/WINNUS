package com.winnus.winnus.src.splash

import android.util.Log
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.src.splash.models.PostLoginCheckResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashService(val view : SplashActivityView) {

    fun tryPostLoginCheck(){
        val splashRetrofitInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SplashRetrofitInterface::class.java)
        splashRetrofitInterface.getLoginCheck().enqueue(object : Callback<PostLoginCheckResponse> {
            override fun onResponse(call: Call<PostLoginCheckResponse>, response: Response<PostLoginCheckResponse>) {
                Log.d("LoginService",response.toString())
                view.onPostLoginCheckSuccess(response.body() as PostLoginCheckResponse)
            }

            override fun onFailure(call: Call<PostLoginCheckResponse>, t: Throwable) {
                view.onPostLoginCheckFailure(t.message ?: "통신 오류")
            }
        })
    }
}