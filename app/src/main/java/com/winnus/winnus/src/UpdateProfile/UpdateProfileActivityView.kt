package com.winnus.winnus.src.UpdateProfile

import com.winnus.winnus.config.BaseResponse

interface UpdateProfileActivityView {

    fun onPatchProfileSuccess(response: BaseResponse)
    fun onPatchProfileFailure(message: String)

    fun deleteProfile()

    fun goToGallery()
}