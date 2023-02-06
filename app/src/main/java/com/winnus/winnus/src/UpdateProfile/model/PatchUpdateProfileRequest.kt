package com.winnus.winnus.src.UpdateProfile.model

import com.google.gson.annotations.SerializedName

data class PatchUpdateProfileRequest(
    @SerializedName("profileImg") val profileImg: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("pwd") val pwd: String?,
    @SerializedName("updatePwd") val updatePwd: String?
)