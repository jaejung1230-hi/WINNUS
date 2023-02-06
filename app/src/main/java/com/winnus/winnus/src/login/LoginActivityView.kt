package com.winnus.winnus.src.login

import com.winnus.winnus.src.login.models.GetSignInResponse

interface LoginActivityView {

    fun onGetSignInSuccess(response: GetSignInResponse)

    fun onGetSignInFailure(message: String)
}