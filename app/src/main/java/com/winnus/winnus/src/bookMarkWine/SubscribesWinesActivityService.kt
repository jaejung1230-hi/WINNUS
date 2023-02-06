package com.winnus.winnus.src.bookMarkWine

import android.util.Log
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.bookMarkWine.model.SebscribesWinesResponse
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubscribesWinesActivityService(private val view: SubscribesWinesActivityView) {

    fun tryGetSubscribesWines(){
        val subscribesWinesActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SubscribesWinesActivityInterface::class.java)
        subscribesWinesActivityInterface.getSubscribesWines().enqueue(object : Callback<SebscribesWinesResponse> {
            override fun onResponse(call: Call<SebscribesWinesResponse>, response: Response<SebscribesWinesResponse>) {
                Log.d("LoginService",response.body().toString())
                view.onGetAllReviewSuccess(response.body() as SebscribesWinesResponse)
            }

            override fun onFailure(call: Call<SebscribesWinesResponse>, t: Throwable) {
                view.onGetAllReviewFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSubscribe(postSubscribeRequest : PostSubscribeRequest){
        val subscribesWinesActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(
            SubscribesWinesActivityInterface::class.java)
        subscribesWinesActivityInterface.postSubscribe(postSubscribeRequest).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("tryPostSubscribe",response.toString())
                view.onPostSubscribeSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostSubscribeFailure(t.message ?: "통신 오류")
            }
        })
    }
}