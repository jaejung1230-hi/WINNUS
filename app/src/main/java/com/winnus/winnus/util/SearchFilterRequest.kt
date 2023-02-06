package com.winnus.winnus.util

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchFilterRequest (
    @SerializedName("keyword") var keyword: String?,
    @SerializedName("type") val type: ArrayList<Int>?,
    @SerializedName("sweetness") val sweetness: Int?,
    @SerializedName("acidity") val acidity: Int?,
    @SerializedName("body") val body: Int?,
    @SerializedName("tannin") val tannin: Int?,
    @SerializedName("flavors") val flavors: ArrayList<Int>?,
    @SerializedName("foods") val foods: ArrayList<Int>?,
    @SerializedName("price") val price: String?,
        ):Parcelable