package com.winnus.winnus.src.main.home.util

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
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.src.WineDetail.DetailWineActivity
import com.winnus.winnus.src.main.home.ThemeWineFragmentView
import com.winnus.winnus.src.main.home.model.ResultPopularWine
import com.winnus.winnus.src.main.home.model.WineSimpleInfo
import com.winnus.winnus.src.main.home.populor.PopularWineFragmentView
import java.text.DecimalFormat

class popularWineGridViewAdapter(private var context: Context, val items: List<ResultPopularWine>, val viewInterface: PopularWineFragmentView): BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(p0: Int): Any = items[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var bookMark = false

        val item = items[p0]
        val inflater : LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view : View = inflater.inflate(R.layout.gridview_item_popular_wine, p2, false)

        val img = view.findViewById<ImageView>(R.id.img_item_wine)
        val name = view.findViewById<TextView>(R.id.tv_item_wine_name)
        val price = view.findViewById<TextView>(R.id.tv_item_wine_price)
        val container = view.findViewById<ConstraintLayout>(R.id.container_popular_wine)
        val heart = view.findViewById<ImageView>(R.id.img_heart)

        Glide.with(context)
            .load(item.wineImg)
            .error(R.drawable.none_img)
            .into(img)

        name.text = item.wineName
        if(item.price == "-1"){
            price.text = "가격정보없음"
        }else{
            val t_dec_up = DecimalFormat("#,###")
            var str_change_money_up = t_dec_up.format(item.price.toInt())
            price.text = str_change_money_up+"원"
        }

        bookMark = item.userSubscribeStatus == "Y"

        if(bookMark){
            heart.setImageResource(R.drawable.ic_winnus_haert_fill_24)
        }else{
            heart.setImageResource(R.drawable.ic_winnus_haert_solid_24)
        }

        container.setOnClickListener {
            val intent = Intent(context, DetailWineActivity::class.java)
            intent.putExtra("wineId", item.wineId)
            intent.putExtra("pos", p0)
            intent.putExtra("sub", item.userSubscribeStatus)
            //context.startActivity(intent)
            (context as Activity).startActivityForResult(intent,1819)
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