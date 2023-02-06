package com.winnus.winnus.src.main.home.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.R
import com.winnus.winnus.config.ApplicationClass.themeWine.themeWineArr
import com.winnus.winnus.src.main.home.ThemeWineFragmentView
import com.winnus.winnus.src.main.home.model.AbsInfo
import com.winnus.winnus.util.ExpandableHeightGridView

class ThemeViewPagerAdapter(private var context: Context, val viewInterface: ThemeWineFragmentView) : RecyclerView.Adapter<ThemeViewPagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.gridviewThemeWine.adapter = themeWineArr[position!!]?.let { themeWineGridViewAdapter(context, it, viewInterface) }
        holder.gridviewThemeWine.isExpanded = true
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.fragment_theme_wine, parent, false)){

        val gridviewThemeWine: ExpandableHeightGridView = itemView.findViewById(R.id.gridview_theme_wine)
    }
}