package com.winnus.winnus.src.WineDetail

import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.WineDetail.models.GetDetailWineResponse

interface DetailWineActivityView {

    fun onGetDetailWineSuccess(response: GetDetailWineResponse)

    fun onGetDetailWineInFailure(message: String)

    fun onPostSubscribeSuccess(response: BaseResponse)
    fun onPostSubscribeFailure(message: String)

    fun onPostReportSuccess(response: BaseResponse)
    fun onPostReportFailure(message: String)

    fun onPostReport(id : Int, reason : Int)


}