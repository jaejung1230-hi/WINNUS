package com.winnus.winnus.src.bookMarkWine.util

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.src.WineDetail.DetailWineActivity
import com.winnus.winnus.src.bookMarkWine.SubscribesWinesActivityView
import com.winnus.winnus.src.bookMarkWine.model.Subscribe
import java.text.DecimalFormat

class SubscribesWinesGridViewAdapter(private var context: Context, val items: List<Subscribe>, val viewInterface : SubscribesWinesActivityView): BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(p0: Int): Any = items[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var bookMark = false

        val item = items[p0]
        val inflater : LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view : View = inflater.inflate(R.layout.gridview_item_wine_search, p2, false)

        val img = view.findViewById<ImageView>(R.id.img_item_wine)
        val heart = view.findViewById<ImageView>(R.id.img_heart)
        val name = view.findViewById<TextView>(R.id.tv_item_wine_name)
        val price = view.findViewById<TextView>(R.id.tv_item_wine_price)
        val container = view.findViewById<ConstraintLayout>(R.id.container_popular_wine)
        val country = view.findViewById<TextView>(R.id.tv_detail_wine_country)
        val region = view.findViewById<TextView>(R.id.tv_detail_wine_region)
        val review = view.findViewById<TextView>(R.id.tv_wine_review)
        val bookmark = view.findViewById<TextView>(R.id.tv_wine_bookmark)

        Glide.with(context)
            .load(item.wineImg)
            .error(R.drawable.none_img)
            .into(img)

        name.text = item.wineName

        if(item.price == "-1"){
            price.text = "??????????????????"
        }else{
            val t_dec_up = DecimalFormat("#,###")
            var str_change_money_up = t_dec_up.format(item.price.toInt())
            price.text = str_change_money_up+"???"
        }

        country.text = item.country
        region.text = item.region
        if(item.reviewCount == 0){
            review.visibility = View.GONE
        }else{
            review.text = "?????? " + item.reviewCount
        }

        bookmark.text = item.subscribeCount.toString()

        bookMark = item.userSubscribeStatus == "Y"

        if(bookMark){
            heart.setImageResource(R.drawable.ic_winnus_haert_fill_24)
        }else{
            heart.setImageResource(R.drawable.ic_winnus_haert_solid_24)
        }

        container.setOnClickListener {
            val intent = Intent(context, DetailWineActivity::class.java)
            intent.putExtra("wineId", item.wineId)
            context.startActivity(intent)
        }

        heart.setOnClickListener {
            viewInterface.tryPostSubscribe(item.wineId)
            bookMark = !bookMark
            if(bookMark){
                heart.setImageResource(R.drawable.ic_winnus_haert_fill_24)
            }else{
                heart.setImageResource(R.drawable.ic_winnus_haert_solid_24)
            }
        }

        return view
    }
}