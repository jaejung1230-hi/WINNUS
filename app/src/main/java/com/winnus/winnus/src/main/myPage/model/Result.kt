package com.winnus.winnus.src.main.myPage.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("nickname")  val nickname: String,
    @SerializedName("phoneNum")  val phoneNum: String,
    @SerializedName("profileImg")  val profileImg: String
)