package com.winnus.winnus.src.ShopDetail.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.databinding.RecyclerItemCategoryAromaFoodBigBinding
import com.winnus.winnus.src.ShopDetail.DetailShopActivityView
import com.winnus.winnus.src.ShopDetail.model.PairingFood

class FoodHorizontalAdapter(private val context: Context, private val items: ArrayList<PairingFood>, private val view : DetailShopActivityView): RecyclerView.Adapter<FoodHorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemCategoryAromaFoodBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position], position, view)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: RecyclerItemCategoryAromaFoodBigBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("ResourceAsColor")
        fun bind(context: Context, item: PairingFood, pos: Int, view: DetailShopActivityView){

            binding.containerPairing.setOnClickListener {
                view.tryGetShopPairing(item.foodCategoryId)
            }
            binding.tvAromaFood.text = item.food
            Glide.with(context)
                .load(item.foodImg)
                .error(R.color.white)
                .into(binding.imgAromaFood)

            if(item.foodCategoryId == -1){
                binding.tvAromaFood.setTextColor(Color.parseColor("#333333"))
                binding.imgAlphaBlack.visibility = View.GONE
                binding.cardImgFood.strokeWidth = 3
            }
        }
    }
}