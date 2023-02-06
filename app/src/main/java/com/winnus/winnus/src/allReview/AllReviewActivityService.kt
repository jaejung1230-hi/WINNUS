package com.winnus.winnus.src.allReview

import android.util.Log
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.WineDetail.DetailWineRetrofitInterface
import com.winnus.winnus.src.allReview.model.AllReviewResponse
import com.winnus.winnus.util.PostReportRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllReviewActivityService(private val view : AllReviewActivityView) {
    fun tryGetAllReview(id : Int){
        val allReviewActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(AllReviewActivityInterface::class.java)
        allReviewActivityInterface.getAllReview(id).enqueue(object : Callback<AllReviewResponse> {
            override fun onResponse(call: Call<AllReviewResponse>, response: Response<AllReviewResponse>) {
                Log.d("AllReviewActivity",response.toString())
                view.onGetAllReviewSuccess(response.body() as AllReviewResponse)
            }

            override fun onFailure(call: Call<AllReviewResponse>, t: Throwable) {
                Log.d("AllReviewActivity",t.toString())
                view.onGetAllReviewFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostReport(postReportRequest : PostReportRequest){
        val detailWineRetrofitInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(
            DetailWineRetrofitInterface::class.java)
        detailWineRetrofitInterface.postReport(postReportRequest).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("tryPostSubscribe",response.toString())
                view.onPostReportSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostReportFailure(t.message ?: "통신 오류")
            }
        })
    }
}