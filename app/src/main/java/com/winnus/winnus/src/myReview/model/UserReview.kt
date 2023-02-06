package com.winnus.winnus.src.myReview.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserReview(
    @SerializedName("review") val review: Review,
    @SerializedName("tags") val tags: ArrayList<Tag>
)