package com.winnus.winnus.src.allReview

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.allReview.model.AllReviewResponse

interface AllReviewActivityView {
    fun onGetAllReviewSuccess(response: AllReviewResponse)
    fun onGetAllReviewFailure(message: String)

    fun onPostReportSuccess(response: BaseResponse)
    fun onPostReportFailure(message: String)

    fun onPostReport(id : Int, reason : Int)
}