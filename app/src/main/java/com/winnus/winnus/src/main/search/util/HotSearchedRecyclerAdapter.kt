package com.winnus.winnus.src.main.search.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.databinding.RecyclerSearchedHotBinding
import com.winnus.winnus.src.main.search.SearchFragmentView
import com.winnus.winnus.src.main.search.model.HotSearched

class HotSearchedRecyclerAdapter(
    private val context: Context,
    private val items: List<HotSearched>,
    private val searchFragmentView: SearchFragmentView
): RecyclerView.Adapter<HotSearchedRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSearchedRecyclerAdapter.ViewHolder {
        val binding = RecyclerSearchedHotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotSearchedRecyclerAdapter.ViewHolder, position: Int) {
        holder.bind(context,items[position], searchFragmentView,position)
    }

    override fun getItemCount(): Int = items.size


    inner class ViewHolder(private var binding: RecyclerSearchedHotBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context, item: HotSearched, searchFragmentView : SearchFragmentView, pos : Int){
            binding.tvKetword.text = item.keyword
            binding.tvRank.text = (pos+1).toString()
            binding.containerKeyWord.setOnClickListener {
                searchFragmentView.clickSearched(item.keyword)
            }

        }
    }
}