package com.winnus.winnus.src.login

import com.winnus.winnus.src.login.models.GetSignInRequest
import com.winnus.winnus.src.login.models.GetSignInResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginRetrofitInterface {
    @POST("app/login")
    fun getSignIn(@Body params: GetSignInRequest): Call<GetSignInResponse>
}