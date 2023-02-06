package com.winnus.winnus.src.myReview.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.databinding.RecyclerKeywordBinding
import com.winnus.winnus.src.myReview.model.Tag

class TagAdapter(private val context: Context, private val items: List<Tag>): RecyclerView.Adapter<TagAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: RecyclerKeywordBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: Tag){
            binding.tvKetword.text = item.content
        }
    }
}