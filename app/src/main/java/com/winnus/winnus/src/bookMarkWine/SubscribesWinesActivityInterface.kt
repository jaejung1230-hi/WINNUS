package com.winnus.winnus.src.bookMarkWine

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.bookMarkWine.model.SebscribesWinesResponse
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SubscribesWinesActivityInterface {
    @GET("app/subscribes")
    fun getSubscribesWines(): Call<SebscribesWinesResponse>

    @POST("app/subscribes")
    fun postSubscribe(@Body params: PostSubscribeRequest): Call<BaseResponse>
}