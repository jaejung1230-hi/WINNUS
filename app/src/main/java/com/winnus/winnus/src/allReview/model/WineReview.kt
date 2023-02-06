package com.winnus.winnus.src.allReview.model

import com.google.gson.annotations.SerializedName

data class WineReview(
    @SerializedName("review") val review: Review,
    @SerializedName("tags") val tags: List<Tag>

)