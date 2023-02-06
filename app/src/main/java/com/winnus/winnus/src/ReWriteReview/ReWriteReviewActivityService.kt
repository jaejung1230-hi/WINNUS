package com.winnus.winnus.src.ReWriteReview

import android.util.Log
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.ReWriteReview.model.PatchReviewRequest
import com.winnus.winnus.src.signup.SignUpRetrofitInterface
import com.winnus.winnus.src.signup.models.GetSignUpRequest
import com.winnus.winnus.src.signup.models.GetSignUpResponse
import com.winnus.winnus.src.writeReview.model.PostReviewRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReWriteReviewActivityService(private val view: ReWriteReviewActivityView) {

    fun tryPostReview(reviewId: Int, patchReviewRequest : PatchReviewRequest){
        val writeReviewActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(ReWriteReviewActivityInterface::class.java)
        writeReviewActivityInterface.postReview(reviewId, patchReviewRequest).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("LoginService",response.body().toString())
                view.onPatchReviewSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPatchReviewFailure(t.message ?: "통신 오류")
            }
        })
    }
}