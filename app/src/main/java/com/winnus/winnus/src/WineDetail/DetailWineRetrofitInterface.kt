package com.winnus.winnus.src.WineDetail

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.WineDetail.models.GetDetailWineResponse
import com.winnus.winnus.util.PostReportRequest
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface DetailWineRetrofitInterface {
    @GET("app/wines/{wineId}")
    fun getDetailInfo(@Path("wineId") wineId : Int) : Call<GetDetailWineResponse>

    @POST("app/subscribes")
    fun postSubscribe(@Body params: PostSubscribeRequest): Call<BaseResponse>

    @POST("app/reviews/report")
    fun postReport(@Body params: PostReportRequest): Call<BaseResponse>
}