package com.winnus.winnus.src.UpdateProfile

import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.UpdateProfile.model.PatchUpdateProfileRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileActivityService(private val view: UpdateProfileActivityView) {

    fun tryPatchUpdateProfile(patchUpdateProfileRequest : PatchUpdateProfileRequest){
        val updateProfileActivityInterface = com.winnus.winnus.config.ApplicationClass.sRetrofit.create(UpdateProfileActivityInterface::class.java)
        updateProfileActivityInterface.patchUpdateProfile(
            com.winnus.winnus.config.ApplicationClass.sSharedPreferences.getInt(
                com.winnus.winnus.config.ApplicationClass.LOG_IN_USER,-1),patchUpdateProfileRequest)
            .enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPatchProfileSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPatchProfileFailure(t.message ?: "통신 오류")
            }
        })
    }
}