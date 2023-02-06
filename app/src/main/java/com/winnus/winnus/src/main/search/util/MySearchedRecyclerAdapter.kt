package com.winnus.winnus.src.main.search.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.databinding.RecyclerKeywordDeleteBinding
import com.winnus.winnus.src.main.search.SearchFragmentView
import com.winnus.winnus.src.main.search.model.Searched

class MySearchedRecyclerAdapter(private val context: Context, private val items: ArrayList<Searched>, private val searchFragmentView : SearchFragmentView): RecyclerView.Adapter<MySearchedRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySearchedRecyclerAdapter.ViewHolder {
        val binding = RecyclerKeywordDeleteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MySearchedRecyclerAdapter.ViewHolder, position: Int) {
        holder.bind(context,items[position], searchFragmentView,position)
    }

    override fun getItemCount(): Int = items.size


    inner class ViewHolder(private var binding: RecyclerKeywordDeleteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context, item: Searched, searchFragmentView : SearchFragmentView, pos : Int){
            binding.tvKeyword.text = item.keyword

            binding.imgKeyword.setOnClickListener {
                items.removeAt(pos)
                notifyItemRemoved(pos)
                notifyItemRangeChanged(pos, itemCount - pos)
                searchFragmentView.deleteSearched(item.searchId)
            }

            binding.containerKeyword.setOnClickListener {
                searchFragmentView.clickSearched(item.keyword)
            }

        }
    }
}