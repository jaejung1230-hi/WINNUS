package com.winnus.winnus.src.writeReview

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.writeReview.model.PostReviewRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface WriteReviewActivityInterface {
    @POST("app/reviews")
    fun postReview(@Body params: PostReviewRequest): Call<BaseResponse>
}