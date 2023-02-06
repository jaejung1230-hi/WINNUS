package com.winnus.winnus.src.shopResult

import com.winnus.winnus.src.shopResult.models.ShopSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShopResultActivityInterface {
    @GET("app/shops")
    fun getShop(@Query("wineName") wineName : String,
                      @Query("area") area : String?) : Call<ShopSearchResponse>
}