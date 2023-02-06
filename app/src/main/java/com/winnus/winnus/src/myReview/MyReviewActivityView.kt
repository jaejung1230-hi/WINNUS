package com.winnus.winnus.src.myReview

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.myReview.model.MyReviewResponse

interface MyReviewActivityView {
    fun onGetAllReviewSuccess(response: MyReviewResponse)
    fun onGetAllReviewFailure(message: String)

    fun onDeleteReviewSuccess(response: BaseResponse)
    fun onDeleteReviewFailure(message: String)

    fun onTryDelete(id : Int)
}