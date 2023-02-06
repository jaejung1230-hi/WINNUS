package com.winnus.winnus.src.myReview.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    @SerializedName("content") val content: String
): Parcelable