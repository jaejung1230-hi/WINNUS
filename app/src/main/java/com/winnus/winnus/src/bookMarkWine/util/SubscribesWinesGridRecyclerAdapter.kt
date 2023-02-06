package com.winnus.winnus.src.bookMarkWine.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.databinding.GridviewItemWineSearchBinding
import com.winnus.winnus.databinding.RecyclerItemWineSearchBinding
import com.winnus.winnus.src.WineDetail.DetailWineActivity
import com.winnus.winnus.src.bookMarkWine.SubscribesWinesActivityView
import com.winnus.winnus.src.bookMarkWine.model.Subscribe
import com.winnus.winnus.src.searchResult.SearchResultActivityView
import com.winnus.winnus.src.searchResult.model.FilteringRe
import com.winnus.winnus.src.searchResult.model.RetrieveWineRes
import java.text.DecimalFormat

class SubscribesWinesGridRecyclerAdapter(private var context: Context, val items: List<Subscribe>, val viewInterface : SubscribesWinesActivityView): RecyclerView.Adapter<SubscribesWinesGridRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GridviewItemWineSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position], viewInterface , position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: GridviewItemWineSearchBinding) : RecyclerView.ViewHolder(binding.root){
        var bookMark = false
        fun bind(context: Context, item: Subscribe, viewInterface: SubscribesWinesActivityView, pos : Int){
            Glide.with(context)
                .load(item.wineImg)
                .error(R.drawable.none_img)
                .into(binding.imgItemWine)

            binding.tvItemWineName.text = item.wineName
            if(item.price == "-1"){
                binding.tvItemWinePrice.text = "가격정보없음"
            }else{
                val t_dec_up = DecimalFormat("#,###")
                var str_change_money_up = t_dec_up.format(item.price.toInt())
                binding.tvItemWinePrice.text = str_change_money_up+"원"
            }
            binding.tvDetailWineCountry.text = item.country
            binding.tvDetailWineRegion.text = item.region
            if(item.reviewCount == 0){
                binding.tvWineReview.visibility = View.GONE
            }else{
                binding.tvWineReview.text = "리뷰 " + item.reviewCount
            }

            binding.tvWineBookmark.text = item.subscribeCount.toString()

            bookMark = item.userSubscribeStatus == "Y"

            if(bookMark){
                binding.imgHeart.setImageResource(R.drawable.ic_winnus_haert_fill_24)
            }else{
                binding.imgHeart.setImageResource(R.drawable.ic_winnus_haert_solid_24)
            }

            binding.containerPopularWine.setOnClickListener {
                val intent = Intent(context, DetailWineActivity::class.java)
                intent.putExtra("wineId", item.wineId)
                intent.putExtra("pos", pos)
                intent.putExtra("sub", item.userSubscribeStatus)
                //context.startActivity(intent)
                (context as Activity).startActivityForResult(intent,1818)
            }

            binding.imgHeart.setOnClickListener {
                viewInterface.tryPostSubscribe(item.wineId)
                bookMark = !bookMark
                if(bookMark){
                    binding.imgHeart.setImageResource(R.drawable.ic_winnus_haert_fill_24)
                }else{
                    binding.imgHeart.setImageResource(R.drawable.ic_winnus_haert_solid_24)
                }
            }
        }
    }
}