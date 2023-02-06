package com.winnus.winnus.src.searchResult

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.searchResult.model.SearchFilterResponse
import com.winnus.winnus.src.searchResult.model.SearchResultResponse
import com.winnus.winnus.util.PostSubscribeRequest
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchResultActivityInterface {
    @GET("app/wines")
    fun getSearchResult(@Query("wineName") wineName : String): Call<SearchResultResponse>

    @GET("app/wines/filter")
    fun getFilterResult(
                        @Query("keyword") keyword: String?,
                        @Query("type") type: String?,
                        @Query("sweetness") sweetness: Int?,
                        @Query("acidity") acidity: Int?,
                        @Query("body") body: Int?,
                        @Query("tannin") tannin: Int?,
                        @Query("flavors") flavors: String?,
                        @Query("foods") foods: String?,
                        @Query("price") price: String?,
                        @Query("page") page: Int,
                        @Query("orderBy") orderBy: String): Call<SearchFilterResponse>

    @POST("app/subscribes")
    fun postSubscribe(@Body params: PostSubscribeRequest): Call<BaseResponse>

}