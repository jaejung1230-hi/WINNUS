package com.winnus.winnus.src.ReWriteReview.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.databinding.RecyclerKeywordDeleteBinding
import com.winnus.winnus.src.ReWriteReview.ReWriteReviewActivityView
import com.winnus.winnus.src.writeReview.WriteReviewActivityView

class KeyWordAdapter(private val context: Context, private val items: ArrayList<Map<String,String>>, val reWriteReviewActivityView : ReWriteReviewActivityView): RecyclerView.Adapter<KeyWordAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerKeywordDeleteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position],reWriteReviewActivityView,position)

    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: RecyclerKeywordDeleteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context, item: Map<String,String>, writeReviewActivityView: ReWriteReviewActivityView, rowNum: Int){
            binding.tvKeyword.text = item["tag"]
            binding.containerKeyword.setOnClickListener{
                writeReviewActivityView.getSelectedTag(rowNum)
            }
        }
    }
}