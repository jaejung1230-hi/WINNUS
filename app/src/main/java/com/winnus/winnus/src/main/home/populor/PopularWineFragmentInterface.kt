package com.winnus.winnus.src.main.home.populor

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.main.home.model.GetPopularWineResponse
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PopularWineFragmentInterface {
    @GET("app/wines/hot")
    fun getPopularWine(@Query("type") type: Int?): Call<GetPopularWineResponse>

    @POST("app/subscribes")
    fun postSubscribe(@Body params: PostSubscribeRequest): Call<BaseResponse>
}