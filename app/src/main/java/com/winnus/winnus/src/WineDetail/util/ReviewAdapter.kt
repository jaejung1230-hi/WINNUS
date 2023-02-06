package com.winnus.winnus.src.WineDetail.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.databinding.RecyclerItemReviewsBinding
import com.winnus.winnus.src.WineDetail.DetailWineActivityView
import com.winnus.winnus.src.WineDetail.ReportDialog
import com.winnus.winnus.src.WineDetail.models.Reviews


class ReviewAdapter(private val context: Context, private val items: List<Reviews>, private val detailWineActivityView: DetailWineActivityView): RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position], detailWineActivityView)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: RecyclerItemReviewsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: Reviews, detailWineActivityView: DetailWineActivityView){
            binding.ratingReview.rating = item.rating
            binding.tvReviewNickName.text = item.userName
            binding.tvReviewDate.text = item.createdAt
            binding.tvReviewContext.text = item.content

            binding.tvReviewReport.setOnClickListener {
                ReportDialog(context, item.reviewId, detailWineActivityView).makeDialog()
            }
        }
    }
}