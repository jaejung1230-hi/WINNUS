package com.winnus.winnus.src.shopResult

import android.util.Log
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.src.shopResult.models.ShopSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopResultActivityService(val view : ShopResultActivityView) {
    fun tryGetShop(wineName : String, area : String?){
        val shopResultActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(ShopResultActivityInterface::class.java)
        shopResultActivityInterface.getShop(wineName,area).enqueue(object : Callback<ShopSearchResponse>{
            override fun onResponse(call: Call<ShopSearchResponse>, response: Response<ShopSearchResponse>) {
                Log.d("ShopResultActivity",response.toString())
                view.onGetShopSuccess(response.body() as ShopSearchResponse)
            }

            override fun onFailure(call: Call<ShopSearchResponse>, t: Throwable) {
                Log.d("ShopResultActivity",t.toString())
                view.onGetShopFailure(t.message ?: "통신 오류")
            }

        })
    }
}