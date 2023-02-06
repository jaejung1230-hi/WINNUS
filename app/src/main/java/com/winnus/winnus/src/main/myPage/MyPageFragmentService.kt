package com.winnus.winnus.src.main.myPage

import com.winnus.winnus.src.main.myPage.model.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragmentService(private val view : MyPageFragmentView) {
    fun tryGetProfile(){
        val myPageFragmentInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(MyPageFragmentInterface::class.java)
        myPageFragmentInterface.getAllReview(
            com.winnus.winnus.config.ApplicationClass.sSharedPreferences.getInt(com.winnus.winnus.config.ApplicationClass.LOG_IN_USER,-1)).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                view.onGetProfileSuccess(response.body() as ProfileResponse)
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                view.onGetProfileFailure(t.message ?: "통신 오류")
            }
        })
    }
}