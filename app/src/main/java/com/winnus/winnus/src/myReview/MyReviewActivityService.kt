package com.winnus.winnus.src.myReview

import android.util.Log
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.ApplicationClass.Companion.LOG_IN_USER
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.allReview.model.AllReviewResponse
import com.winnus.winnus.src.myReview.model.MyReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReviewActivityService(private val view : MyReviewActivityView) {
    fun tryGetAllReview(){
        val myReviewActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(MyReviewActivityInterface::class.java)
        myReviewActivityInterface.getAllReview(com.winnus.winnus.config.ApplicationClass.sSharedPreferences.getInt(LOG_IN_USER,-1)).enqueue(object : Callback<MyReviewResponse> {
            override fun onResponse(call: Call<MyReviewResponse>, response: Response<MyReviewResponse>) {
                Log.d("AllReviewActivity",response.toString())
                view.onGetAllReviewSuccess(response.body() as MyReviewResponse)
            }

            override fun onFailure(call: Call<MyReviewResponse>, t: Throwable) {
                Log.d("AllReviewActivity",t.toString())
                view.onGetAllReviewFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchReview(id : Int){
        val myReviewActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(MyReviewActivityInterface::class.java)
        myReviewActivityInterface.patchReview(id).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("AllReviewActivity",response.toString())
                view.onDeleteReviewSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("AllReviewActivity",t.toString())
                view.onDeleteReviewFailure(t.message ?: "통신 오류")
            }
        })
    }
}