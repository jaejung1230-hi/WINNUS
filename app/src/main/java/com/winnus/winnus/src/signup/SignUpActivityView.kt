package com.winnus.winnus.src.signup

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.signup.models.GetSignUpResponse

interface SignUpActivityView {

    fun onGetSignUpSuccess(response: GetSignUpResponse)
    fun onGetSignUpFailure(message: String)

    fun onPostCodeSuccess(response: BaseResponse)
    fun onPostCodeFailure(message: String)

    fun onPostCodeCheckSuccess(response: BaseResponse)
    fun onPostCodeCheckFailure(message: String)


}