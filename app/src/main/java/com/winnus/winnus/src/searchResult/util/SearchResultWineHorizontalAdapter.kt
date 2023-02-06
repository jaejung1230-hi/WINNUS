package com.winnus.winnus.src.searchResult.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.databinding.RecyclerItemWineSearchBinding
import com.winnus.winnus.src.WineDetail.DetailWineActivity
import com.winnus.winnus.src.searchResult.SearchResultActivityView
import com.winnus.winnus.src.searchResult.model.RetrieveWineRes
import java.text.DecimalFormat

class SearchResultWineHorizontalAdapter(private val context: Context, private val items: List<RetrieveWineRes>, val viewInterface: SearchResultActivityView): RecyclerView.Adapter<SearchResultWineHorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemWineSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position], viewInterface, position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: RecyclerItemWineSearchBinding) : RecyclerView.ViewHolder(binding.root){
        var bookMark = false
        fun bind(context: Context, item: RetrieveWineRes, viewInterface: SearchResultActivityView, pos: Int){
            Glide.with(context)
                .load(item.wineImg)
                .error(R.drawable.none_img)
                .into(binding.imgItemWine)

            binding.tvItemWineName.text = item.wineName
            if(item.price == "-1"){
                binding.tvWinePrice.text = "가격정보없음"
            }else{
                val t_dec_up = DecimalFormat("#,###")
                var str_change_money_up = t_dec_up.format(item.price.toInt())
                binding.tvWinePrice.text = str_change_money_up+"원"
            }
            binding.tvDetailWineCountry.text = item.country
            binding.tvDetailWineRegion.text = item.region
            binding.tvWineQuantity.text = item.quantity

            bookMark = item.userSubscribeStatus == "Y"

            if(bookMark){
                binding.imgHeart.setImageResource(R.drawable.ic_winnus_haert_fill_24)
            }else{
                binding.imgHeart.setImageResource(R.drawable.ic_winnus_haert_solid_white_24)
            }

            binding.containerSearchResult.setOnClickListener {
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
                    binding.imgHeart.setImageResource(R.drawable.ic_winnus_haert_solid_white_24)
                }
            }
        }
    }
}