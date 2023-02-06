package com.winnus.winnus.src.shopIntro

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.databinding.ActivityIntroShopDetailBinding
import com.winnus.winnus.src.ShopDetail.DetailShopActivity
import com.winnus.winnus.src.shopResult.models.Shop

class ShopIntroActivity: com.winnus.winnus.config.BaseActivity<ActivityIntroShopDetailBinding>(ActivityIntroShopDetailBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shop = intent.getParcelableExtra<Shop>("data")
        binding.imgBackground.setImageResource(R.drawable.temp_shop)

        binding.tvShopIntroKind.text = shop?.shopCategory
        binding.tvShopIntroLocation.text = shop?.location
        binding.tvShopIntroName.text = shop?.shopName
        binding.tvShopIntroTel.text = shop?.tel

        Glide.with(this)
            .load(shop?.shopImg)
            .into(binding.imgBackground)

        binding.btnCall.setOnClickListener {
            val tel = "tel:${shop?.tel}"
            startActivity(Intent("android.intent.action.DIAL", Uri.parse(tel)))
        }
        binding.btnDetailShop.setOnClickListener {
            val intent = Intent(this,DetailShopActivity::class.java)
            intent.putExtra("shopId",shop?.shopId)
            intent.putExtra("shopImg",shop?.shopImg)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                binding.imgBackground, binding.imgBackground.transitionName
            )

            startActivity(intent,options.toBundle())
        }
    }
}