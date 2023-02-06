package com.winnus.winnus.src.ShopDetail

import android.util.Log
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.ShopDetail.model.DetailShopResponse
import com.winnus.winnus.src.ShopDetail.model.ShopPairingResponse
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailShopActivityService(val view : DetailShopActivityView) {
    fun tryGetDetailShop(shopId : Int){
        val detailShopActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(DetailShopActivityInterface::class.java)

        detailShopActivityInterface.getShop(shopId).enqueue(object : Callback<DetailShopResponse> {
            override fun onResponse(call: Call<DetailShopResponse>, response: Response<DetailShopResponse>) {
                Log.d("DetailShopActivity",response.toString())
                view.onGetShopDetailSuccess(response.body() as DetailShopResponse)
            }

            override fun onFailure(call: Call<DetailShopResponse>, t: Throwable) {
                Log.d("DetailShopActivity",t.toString())
                view.onGetShopDetailFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetShopPairingShop(shopId : Int,foodId : Int){
        val detailShopActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(DetailShopActivityInterface::class.java)

        detailShopActivityInterface.getShopPairing(shopId, foodId).enqueue(object : Callback<ShopPairingResponse> {
            override fun onResponse(call: Call<ShopPairingResponse>, response: Response<ShopPairingResponse>) {
                Log.d("DetailShopActivity",response.toString())
                view.onGetShopPairingSuccess(response.body() as ShopPairingResponse)
            }

            override fun onFailure(call: Call<ShopPairingResponse>, t: Throwable) {
                Log.d("DetailShopActivity",t.toString())
                view.onGetShopPairingFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostSubscribe(postSubscribeRequest : PostSubscribeRequest){
        val detailShopActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(DetailShopActivityInterface::class.java)
        detailShopActivityInterface.postSubscribe(postSubscribeRequest).enqueue(object : Callback<BaseResponse>{
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