package com.winnus.winnus.src.allReview.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.databinding.RecyclerItemReviewsBinding
import com.winnus.winnus.src.allReview.model.WineReview
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.winnus.winnus.src.allReview.AllReviewActivityView
import com.winnus.winnus.src.allReview.ReportDialog

class AllReviewAdapter(private val context: Context, private val items: List<WineReview>, private val allReviewActivityView: AllReviewActivityView): RecyclerView.Adapter<AllReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position], allReviewActivityView)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: RecyclerItemReviewsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: WineReview, allReviewActivityView: AllReviewActivityView){
            binding.ratingReview.rating = item.review.rating
            binding.tvReviewNickName.text = item.review.userName
            binding.tvReviewDate.text = item.review.createdAt
            binding.tvReviewContext.text = item.review.content

            binding.tvReviewReport.setOnClickListener {
                ReportDialog(context, item.review.reviewId, allReviewActivityView).makeDialog()
            }

            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP // 아이템크기 유지, multiLine
                flexDirection = FlexDirection.ROW // 가로 방향 정렬
                justifyContent = JustifyContent.FLEX_START // 시작기준 정렬
            }.let {
                binding.recyclerReviewKeyword.layoutManager = it
                binding.recyclerReviewKeyword.adapter = TagAdapter(context, item.tags )
            }
        }
    }
}