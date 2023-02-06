package com.winnus.winnus.src.signup

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.signup.models.GetCodeCheckRequest
import com.winnus.winnus.src.signup.models.GetCodeRequest
import com.winnus.winnus.src.signup.models.GetSignUpRequest
import com.winnus.winnus.src.signup.models.GetSignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface SignUpRetrofitInterface {
    @POST("app/users")
    fun getSignUp(@Body params: GetSignUpRequest): Call<GetSignUpResponse>
    @POST("app/verifications")
    fun getCode(@Body params: GetCodeRequest): Call<BaseResponse>
    @POST("app/verifications/verify")
    fun getCodeCheck(@Body params: GetCodeCheckRequest): Call<BaseResponse>
}