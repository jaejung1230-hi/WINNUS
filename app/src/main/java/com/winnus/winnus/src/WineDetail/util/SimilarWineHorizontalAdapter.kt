package com.winnus.winnus.src.WineDetail.util


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.databinding.RecyclerItemWineBinding
import com.winnus.winnus.databinding.RecyclerItemWineForDetailBinding
import com.winnus.winnus.src.WineDetail.DetailWineActivity
import com.winnus.winnus.src.WineDetail.models.BestWineListByType
import com.winnus.winnus.src.WineDetail.models.SimilarWineList
import com.winnus.winnus.src.main.home.home.HomeFragmentView
import com.winnus.winnus.src.main.home.model.ResultTodayWine
import java.text.DecimalFormat

class SimilarWineHorizontalAdapter(private val context: Context, private val items: List<SimilarWineList>): RecyclerView.Adapter<SimilarWineHorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemWineForDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: RecyclerItemWineForDetailBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context, item: SimilarWineList){
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
                binding.tvItemWinePrice.text = str_change_money_up
            }

            binding.tvDetailWineCountry.text = item.country
            binding.tvDetailWineRegion.text = item.region

            binding.containerWineForDetail.setOnClickListener {
                val intent = Intent(context, DetailWineActivity::class.java)
                intent.putExtra("wineId", item.wineId)
                context.startActivity(intent)
            }
        }
    }
}