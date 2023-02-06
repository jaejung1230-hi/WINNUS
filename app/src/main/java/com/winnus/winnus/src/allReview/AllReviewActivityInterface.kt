package com.winnus.winnus.src.allReview

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.allReview.model.AllReviewResponse
import com.winnus.winnus.util.PostReportRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AllReviewActivityInterface {
    @GET("app/reviews/{wineId}")
    fun getAllReview(@Path("wineId") wineId : Int): Call<AllReviewResponse>

    @POST("app/reviews/report")
    fun postReport(@Body params: PostReportRequest): Call<BaseResponse>
}