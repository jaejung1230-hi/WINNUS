package com.winnus.winnus.src.main.home

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.main.home.model.GetThemeWineResponse
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ThemeWineFragmentInterface {

    @GET("app/wines/theme/")
    fun getThemeWine(@Query("theme") theme : String): Call<GetThemeWineResponse>

    @POST("app/subscribes")
    fun postSubscribe(@Body params: PostSubscribeRequest): Call<BaseResponse>
}