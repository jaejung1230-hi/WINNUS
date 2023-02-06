package com.winnus.winnus.src.main.home.home

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.main.home.model.GetTodayWineResponse
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeFragmentInterface {

    @GET("app/wines/today")
    fun getTodayWine(): Call<GetTodayWineResponse>

    @POST("app/subscribes")
    fun postSubscribe(@Body params: PostSubscribeRequest): Call<BaseResponse>

}