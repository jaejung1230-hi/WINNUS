package com.winnus.winnus.src.withDraw

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.withDraw.model.PatchWithDrawRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface WithDrawActivityInterface {
    @PATCH("app/users/withdraw")
    fun patchWithDraw(@Body params : PatchWithDrawRequest) : Call<BaseResponse>
}