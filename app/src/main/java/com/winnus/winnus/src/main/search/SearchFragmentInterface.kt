package com.winnus.winnus.src.main.search

import com.winnus.winnus.src.main.search.model.*
import retrofit2.Call
import retrofit2.http.*

interface SearchFragmentInterface {
    @GET("app/wineNames")
    fun getThemeWine(): Call<GetWineNameResponse>

    @POST("app/searched")
    fun postSearched(@Body params: PostSearchedRequest): Call<postSearchedResponse>

    @GET("app/searched")
    fun getSearched(): Call<GetSearchedResponse>

    @GET("app/searched/hot")
    fun getHotSearched(): Call<HotSearchedResponse>

    @PATCH("app/searched")
    fun patchSearched(@Query("searchId") searchId : Int?): Call<PatchSearchedResponse>
}