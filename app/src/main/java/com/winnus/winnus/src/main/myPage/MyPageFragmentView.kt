package com.winnus.winnus.src.main.myPage

import com.winnus.winnus.src.main.myPage.model.ProfileResponse

interface MyPageFragmentView {
    fun onGetProfileSuccess(response: ProfileResponse)
    fun onGetProfileFailure(message: String)
}