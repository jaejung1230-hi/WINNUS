package com.winnus.winnus.src.splash

import com.winnus.winnus.src.splash.models.PostLoginCheckResponse

interface SplashActivityView {

    fun onPostLoginCheckSuccess(response: PostLoginCheckResponse)
    fun onPostLoginCheckFailure(message: String)

}