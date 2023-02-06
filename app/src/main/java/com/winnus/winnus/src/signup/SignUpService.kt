package com.winnus.winnus.src.signup

import android.util.Log
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.signup.models.GetCodeCheckRequest
import com.winnus.winnus.src.signup.models.GetCodeRequest
import com.winnus.winnus.src.signup.models.GetSignUpRequest
import com.winnus.winnus.src.signup.models.GetSignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpService(val view : SignUpActivityView) {

    fun tryGetSignUp(getSignUpRequest : GetSignUpRequest){
        val signUpRetrofitInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.getSignUp(getSignUpRequest).enqueue(object : Callback<GetSignUpResponse> {
            override fun onResponse(call: Call<GetSignUpResponse>, response: Response<GetSignUpResponse>) {
                Log.d("LoginService",response.body().toString())
                view.onGetSignUpSuccess(response.body() as GetSignUpResponse)
            }

            override fun onFailure(call: Call<GetSignUpResponse>, t: Throwable) {
                view.onGetSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetCode(getCodeRequest : GetCodeRequest){
        val signUpRetrofitInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.getCode(getCodeRequest).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("LoginService",response.body().toString())
                view.onPostCodeSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostCodeFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetCodeCheck(getCodeCheckRequest : GetCodeCheckRequest){
        val signUpRetrofitInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(SignUpRetrofitInterface::class.java)
        signUpRetrofitInterface.getCodeCheck(getCodeCheckRequest).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("LoginService",response.body().toString())
                view.onPostCodeCheckSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostCodeCheckFailure(t.message ?: "통신 오류")
            }
        })
    }
}