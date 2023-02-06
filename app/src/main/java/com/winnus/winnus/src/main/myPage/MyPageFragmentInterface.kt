package com.winnus.winnus.src.main.myPage

import com.winnus.winnus.src.main.myPage.model.ProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageFragmentInterface {
    @GET("app/users/{userId}")
    fun getAllReview(@Path("userId") userId : Int): Call<ProfileResponse>
}