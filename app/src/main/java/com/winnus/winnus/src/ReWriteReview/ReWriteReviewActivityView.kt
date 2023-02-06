package com.winnus.winnus.src.ReWriteReview

import com.winnus.winnus.config.BaseResponse

interface ReWriteReviewActivityView {

    fun onPatchReviewSuccess(response: BaseResponse)
    fun onPatchReviewFailure(message: String)
    fun getSelectedTag(num: Int)
}