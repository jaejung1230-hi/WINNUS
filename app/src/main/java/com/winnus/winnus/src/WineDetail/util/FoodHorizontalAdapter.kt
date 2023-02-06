package com.winnus.winnus.src.WineDetail.util


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.databinding.RecyclerItemCategoryAromaFoodBinding
import com.winnus.winnus.src.WineDetail.models.PairingFoodList

class FoodHorizontalAdapter(private val context: Context, private val items: List<PairingFoodList>): RecyclerView.Adapter<FoodHorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemCategoryAromaFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: RecyclerItemCategoryAromaFoodBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: PairingFoodList){
            binding.tvAromaFood.text = item.food
            Glide.with(context)
                .load(item.foodImg)
                .error(R.color.black)
                .into(binding.imgAromaFood)

        }
    }
}