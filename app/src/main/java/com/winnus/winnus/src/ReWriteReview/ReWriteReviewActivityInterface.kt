package com.winnus.winnus.src.ReWriteReview

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.ReWriteReview.model.PatchReviewRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ReWriteReviewActivityInterface {
    @PATCH("app/reviews/{reviewId}")
    fun postReview(@Path("reviewId") reviewId: Int, @Body params: PatchReviewRequest): Call<BaseResponse>
}