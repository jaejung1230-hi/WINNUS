package com.winnus.winnus.src.main.home.util


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.databinding.RecyclerItemWineBinding
import com.winnus.winnus.src.WineDetail.DetailWineActivity
import com.winnus.winnus.src.main.home.home.HomeFragmentView
import com.winnus.winnus.src.main.home.model.ResultTodayWine

class TodayWineHorizontalAdapter(private val context: Context, private val items: ArrayList<ResultTodayWine>, val viewInterface: HomeFragmentView): RecyclerView.Adapter<TodayWineHorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemWineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context,items[position], viewInterface, position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var binding: RecyclerItemWineBinding) : RecyclerView.ViewHolder(binding.root){
        var bookMark = false
        fun bind(context: Context, item: ResultTodayWine, viewInterface: HomeFragmentView, pos: Int){
            Glide.with(context)
                .load(item.wineImg)
                .error(R.drawable.none_img)
                .into(binding.imgItemWine)

            binding.tvItemWineName.text = item.wineName

            binding.tvDetailWineCountry.text = item.country
            binding.tvDetailWineRegion.text = item.region

            bookMark = item.userSubscribeStatus == "Y"

            if(bookMark){
                binding.imgHeart.setImageResource(R.drawable.ic_winnus_haert_fill_24)
            }else{
                binding.imgHeart.setImageResource(R.drawable.ic_winnus_haert_solid_24)
            }

            binding.containerTodayWine.setOnClickListener {
                val intent = Intent(context, DetailWineActivity::class.java)
                intent.putExtra("wineId", item.wineId)
                intent.putExtra("pos", pos)
                intent.putExtra("sub", item.userSubscribeStatus)
                //context.startActivity(intent)
                (context as Activity).startActivityForResult(intent,1820)
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