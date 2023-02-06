package com.winnus.winnus.src.WineDetail

import android.util.Log
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.WineDetail.models.GetDetailWineResponse
import com.winnus.winnus.src.searchResult.SearchResultActivityInterface
import com.winnus.winnus.util.PostReportRequest
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailWineService(val view : DetailWineActivityView) {

    fun tryGetDetailWine(wineId : Int){
        val detailWineRetrofitInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(DetailWineRetrofitInterface::class.java)

        detailWineRetrofitInterface.getDetailInfo(wineId).enqueue(object : Callback<GetDetailWineResponse> {
            override fun onResponse(call: Call<GetDetailWineResponse>, response: Response<GetDetailWineResponse>) {
                Log.d("DetailWine",response.toString())
                view.onGetDetailWineSuccess(response.body() as GetDetailWineResponse)
            }

            override fun onFailure(call: Call<GetDetailWineResponse>, t: Throwable) {
                Log.d("DetailWine",t.toString())
                view.onGetDetailWineInFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSubscribe(postSubscribeRequest : PostSubscribeRequest){
        val detailWineRetrofitInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(
            DetailWineRetrofitInterface::class.java)
        detailWineRetrofitInterface.postSubscribe(postSubscribeRequest).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("tryPostSubscribe",response.toString())
                view.onPostSubscribeSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostSubscribeFailure(t.message ?: "통신 오류")
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