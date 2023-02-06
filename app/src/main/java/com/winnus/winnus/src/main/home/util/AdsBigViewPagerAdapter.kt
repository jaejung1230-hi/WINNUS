package com.winnus.winnus.src.main.home.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.R
import com.winnus.winnus.src.main.home.model.AbsInfo
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat


class AdsBigViewPagerAdapter(private val context: Context, itemList: List<AbsInfo>) : RecyclerView.Adapter<AdsBigViewPagerAdapter.PagerViewHolder>() {
    var item = itemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.abs.setImageResource(item[position%(item.size)].img)
        holder.title.text = item[position%(item.size)].title
        holder.subTitle.text = item[position%(item.size)].subTitle

        holder.container_abs.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item[position%(item.size)].url))
            context.startActivity(intent)
        }
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.view_pager_ads_big, parent, false)){
        val abs: ImageView = itemView.findViewById(R.id.img_abs)
        val title: TextView = itemView.findViewById(R.id.tv_abs_banner)
        val subTitle: TextView = itemView.findViewById(R.id.tv_abs_banner_sub)
        val container_abs: ConstraintLayout = itemView.findViewById(R.id.container_abs)

    }
}