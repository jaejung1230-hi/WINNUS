package com.winnus.winnus.src.splash

import com.winnus.winnus.src.splash.models.PostLoginCheckResponse
import retrofit2.Call
import retrofit2.http.POST


interface SplashRetrofitInterface {
    @POST("app/auto-login")
    fun getLoginCheck(): Call<PostLoginCheckResponse>
}