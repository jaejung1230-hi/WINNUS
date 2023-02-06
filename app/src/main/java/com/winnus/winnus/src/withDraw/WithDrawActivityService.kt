package com.winnus.winnus.src.withDraw

import android.util.Log
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.withDraw.model.PatchWithDrawRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WithDrawActivityService(val view : WithDrawActivityView) {
    fun tryPatchWithDraw(patchWithDrawRequest : PatchWithDrawRequest){
        val withDrawActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(WithDrawActivityInterface::class.java)
        withDrawActivityInterface.patchWithDraw(patchWithDrawRequest).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("ShopResultActivity",response.toString())
                view.onWithDrawSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("ShopResultActivity",t.toString())
                view.onWithDrawFailure(t.message ?: "통신 오류")
            }

        })
    }
}