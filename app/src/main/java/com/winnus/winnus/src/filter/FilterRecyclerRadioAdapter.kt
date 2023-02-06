package com.winnus.winnus.src.filter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.R
import com.winnus.winnus.databinding.RecyclerItemFilterBinding

class FilterRecyclerRadioAdapter(private val context: Context, private val items: List<String>, private val num: Int): RecyclerView.Adapter<FilterRecyclerRadioAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterRecyclerRadioAdapter.ViewHolder {
        val binding = RecyclerItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterRecyclerRadioAdapter.ViewHolder, position: Int) {
        holder.bind(context,items[position],num, position)
    }

    override fun getItemCount(): Int = items.size


    inner class ViewHolder(private var binding: RecyclerItemFilterBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context, item: String, num : Int, position : Int){
            binding.tvFilter.text = item

            if(com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[num][position]){
                binding.tvFilter.setTextColor(Color.parseColor("#FFFFFF"))
                binding.tvFilter.setBackgroundResource(R.drawable.bg_gray_solid)
            }else{
                binding.tvFilter.setTextColor(Color.parseColor("#333333"))
                binding.tvFilter.setBackgroundResource(R.drawable.bg_gray_stroke)
            }

            binding.tvFilter.setOnClickListener {

                if(!com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[num][position]){
                    binding.tvFilter.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.tvFilter.setBackgroundResource(R.drawable.bg_gray_solid)
                    com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[num][position] = true
                }else{
                    binding.tvFilter.setTextColor(Color.parseColor("#333333"))
                    binding.tvFilter.setBackgroundResource(R.drawable.bg_gray_stroke)
                    com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[num][position] = false
                }

                for(i in 0 until com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[num].size){
                    if(i != position){
                        com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[num][i] = false
                    }
                }
                notifyDataSetChanged()
            }
        }
    }
}