package com.winnus.winnus.src.WineDetail.models

import com.google.gson.annotations.SerializedName

data class WineInfo(
    @SerializedName("wineImg") val wineImg: String,
    @SerializedName("wineName") val wineName: String,
    @SerializedName("inEnglish") val inEnglish: String,
    @SerializedName("country") val country: String,
    @SerializedName("region") val region: String,
    @SerializedName("quantity") val quantity: String,
    @SerializedName("productionYear") val productionYear: String,
    @SerializedName("price") val price: String,
    @SerializedName("sweetness") val sweetness: Int,
    @SerializedName("acidity") val acidity: Int,
    @SerializedName("body") val body: Int,
    @SerializedName("tannin") val tannin: Int,
    @SerializedName("taste") val taste: String,
    @SerializedName("typeId") val typeId: Int,
    @SerializedName("type") val type: String,
    @SerializedName("variety") val variety: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("reviewNum") val reviewNum: Int,
    @SerializedName("userSubscribeStatus") val userSubscribeStatus: String
)