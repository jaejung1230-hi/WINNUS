package com.winnus.winnus.src.shopResult

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Pair.create
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.databinding.RecyclerItemShopSearchBinding
import com.winnus.winnus.src.shopIntro.ShopIntroActivity
import com.winnus.winnus.src.shopResult.models.Shop
import androidx.core.app.ActivityOptionsCompat
import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult
import com.bumptech.glide.Glide
import com.winnus.winnus.R


class ShopAdapter(private val context: Context, private val items: List<Shop>): RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemShopSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: RecyclerItemShopSearchBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context, item: Shop){

            Glide.with(context)
                .load(item.shopImg)
                .into(binding.imgItemShop)

            binding.tvShopName.text = item.shopName
            binding.tvShopCategory.text = item.shopCategory
            binding.tvShopLocation.text = item.location
            binding.tvShopTel.text = item.tel

            binding.containerSearchResult.setOnClickListener {
                val intent = Intent(context, ShopIntroActivity::class.java)
                intent.putExtra("data", item)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity,
                    binding.imgItemShop, binding.imgItemShop.transitionName
                )

                context.startActivity(intent,options.toBundle())
            }

        }
    }
}