package com.winnus.winnus.src.writeReview

import com.winnus.winnus.config.BaseResponse

interface WriteReviewActivityView {

    fun onPostReviewSuccess(response: BaseResponse)
    fun onPostReviewFailure(message: String)
    fun getSelectedTag(num: Int)
}