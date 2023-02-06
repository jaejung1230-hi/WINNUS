package com.winnus.winnus.src.UpdateProfile

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.UpdateProfile.model.PatchUpdateProfileRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UpdateProfileActivityInterface {
    @PATCH("app/users/{userId}")
    fun patchUpdateProfile(@Path("userId") userId : Int, @Body prams : PatchUpdateProfileRequest): Call<BaseResponse>
}