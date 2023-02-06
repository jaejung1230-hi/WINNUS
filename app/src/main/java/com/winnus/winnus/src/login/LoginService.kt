package com.example.risingproject.src.emailLogin

import android.util.Log
import com.winnus.winnus.src.login.LoginActivityView
import com.winnus.winnus.src.login.LoginRetrofitInterface
import com.winnus.winnus.src.login.models.GetSignInRequest
import com.winnus.winnus.src.login.models.GetSignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginService(val view : LoginActivityView) {

    fun tryGetSignIn(getSignInRequest : GetSignInRequest){
        val signupOneRetrofitInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)

        signupOneRetrofitInterface.getSignIn(getSignInRequest).enqueue(object : Callback<GetSignInResponse> {
            override fun onResponse(call: Call<GetSignInResponse>, response: Response<GetSignInResponse>) {
                Log.d("LoginService",response.body().toString())
                view.onGetSignInSuccess(response.body() as GetSignInResponse)
            }

            override fun onFailure(call: Call<GetSignInResponse>, t: Throwable) {
                view.onGetSignInFailure(t.message ?: "통신 오류")
            }
        })
    }
}