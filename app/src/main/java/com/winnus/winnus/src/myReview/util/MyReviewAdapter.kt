package com.winnus.winnus.src.myReview.util

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.databinding.RecyclerItemMyReviewsBinding
import com.winnus.winnus.src.myReview.model.UserReview
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import android.view.MenuItem
import android.widget.PopupMenu
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.src.ReWriteReview.ReWriteReviewActivity
import com.winnus.winnus.src.myReview.MyReviewActivityView


class MyReviewAdapter(private val context: Context, private val items: ArrayList<UserReview>, private val myReviewActivityView: MyReviewActivityView): RecyclerView.Adapter<MyReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemMyReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position],position)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private var binding: RecyclerItemMyReviewsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: UserReview, pos : Int){
            binding.ratingReview.rating = item.review.rating
            binding.tvReviewNickName.text = item.review.userName
            binding.tvReviewDate.text = item.review.createdAt
            binding.tvReviewContext.text = item.review.content

            binding.tvReviewItemContury.text = item.review.country
            binding.tvReviewItemRegion.text = item.review.region
            binding.tvReviewItemName.text = item.review.wineName

            Glide.with(context)
                .load(item.review.wineImg)
                .error(R.drawable.temp_wine)
                .into(binding.imgReviewWine)

            FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP // 아이템크기 유지, multiLine
                flexDirection = FlexDirection.ROW // 가로 방향 정렬
                justifyContent = JustifyContent.FLEX_START // 시작기준 정렬
            }.let {
                binding.recyclerReviewKeyword.layoutManager = it
                binding.recyclerReviewKeyword.adapter = TagAdapter(context, item.tags)
            }



            binding.btnOptions.setOnClickListener {
                val popup = PopupMenu(context, binding.btnOptions)
                popup.inflate(R.menu.menu_review)
                popup.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu_review_delete ->{
                            items.removeAt(pos)
                            notifyItemRemoved(pos)
                            notifyItemRangeChanged(pos, itemCount - pos)
                            myReviewActivityView.onTryDelete(item.review.reviewId)
                            false
                        }
                        R.id.menu_review_update ->{
                            val intent = Intent(context,ReWriteReviewActivity::class.java)
                            intent.putExtra("content",item.review.content)
                            intent.putExtra("rating",item.review.rating)
                            intent.putExtra("reviewId",item.review.reviewId)
                            intent.putExtra("tags",item.tags)
                            intent.putExtra("country",item.review.country)
                            intent.putExtra("region",item.review.region)
                            intent.putExtra("wineName",item.review.wineName)
                            intent.putExtra("wineImg",item.review.wineImg)

                            context.startActivity(intent)
                            true
                        }
                        else -> false
                    }
                }

                popup.show()
            }
        }
    }
}