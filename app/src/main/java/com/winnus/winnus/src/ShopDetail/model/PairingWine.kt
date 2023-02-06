package com.winnus.winnus.src.ShopDetail.model

data class PairingWine(
    val country: String,
    val price: Int,
    val quantity: String,
    val region: String,
    val reviewCount: Int,
    val subscribeCount: Int,
    val userSubscribeStatus: String,
    val wineId: Int,
    val wineImg: String,
    val wineName: String
)