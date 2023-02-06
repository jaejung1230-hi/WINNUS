package com.winnus.winnus.src.myReview

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.myReview.model.MyReviewResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MyReviewActivityInterface {
    @GET("app/users/reviews/{userId}")
    fun getAllReview(@Path("userId") userId : Int): Call<MyReviewResponse>

    @PATCH("app/users/reviews/{reviewId}")
    fun patchReview(@Path("reviewId") reviewId : Int): Call<BaseResponse>
}