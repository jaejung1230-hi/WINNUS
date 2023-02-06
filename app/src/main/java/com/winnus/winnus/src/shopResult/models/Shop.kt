package com.winnus.winnus.src.shopResult.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Shop(
    @SerializedName("location") val location: String,
    @SerializedName("shopCategory") val shopCategory: String,
    @SerializedName("shopId") val shopId: Int,
    @SerializedName("shopName") val shopName: String,
    @SerializedName("shopImg") val shopImg: String,
    @SerializedName("tel") val tel: String
):Parcelable