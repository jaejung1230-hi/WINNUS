package com.winnus.winnus.src.main.home.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.winnus.winnus.R
import com.winnus.winnus.src.main.home.model.ResultPopularWine
import com.winnus.winnus.src.main.home.model.WineSimpleInfo
import com.winnus.winnus.src.main.home.populor.PopularWineFragmentView
import com.winnus.winnus.util.ExpandableHeightGridView

class popularWineViewPagerAdapter(private val context: Context ,private val  itemList: ArrayList<ArrayList<ResultPopularWine>>,val viewInterface: PopularWineFragmentView) : RecyclerView.Adapter<popularWineViewPagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.gridview.adapter = popularWineGridViewAdapter(context,itemList[position], viewInterface)
        holder.gridview.isExpanded = true
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.gridview_popular_wine, parent, false)){
        val gridview : ExpandableHeightGridView = itemView.findViewById(R.id.gridview_popular_wine)
    }
}