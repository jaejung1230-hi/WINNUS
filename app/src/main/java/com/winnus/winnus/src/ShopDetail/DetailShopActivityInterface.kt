package com.winnus.winnus.src.ShopDetail

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.ShopDetail.model.DetailShopResponse
import com.winnus.winnus.src.ShopDetail.model.ShopPairingResponse
import com.winnus.winnus.util.PostSubscribeRequest
import retrofit2.Call
import retrofit2.http.*

interface DetailShopActivityInterface {
    @GET("app/shops/{shopId}")
    fun getShop(@Path("shopId") shopId : Int) : Call<DetailShopResponse>

    @POST("app/subscribes")
    fun postSubscribe(@Body params: PostSubscribeRequest): Call<BaseResponse>

    @GET("app/shops/{shopId}/pairing/{foodId}")
    fun getShopPairing(@Path("shopId") shopId : Int,@Path("foodId") foodId : Int) : Call<ShopPairingResponse>

}