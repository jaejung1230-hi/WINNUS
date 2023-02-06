package com.winnus.winnus.src.ShopDetail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.databinding.ActivityDetailShopBinding
import com.winnus.winnus.src.ShopDetail.model.DetailShopResponse
import com.winnus.winnus.src.ShopDetail.model.PairingFood
import com.winnus.winnus.src.ShopDetail.model.ShopPairingResponse
import com.winnus.winnus.src.ShopDetail.model.Wine
import com.winnus.winnus.src.ShopDetail.util.FoodHorizontalAdapter
import com.winnus.winnus.src.ShopDetail.util.ShopWinesGridRecyclerAdapter
import com.winnus.winnus.src.ShopDetail.util.ShopWinesGridViewAdapter
import com.winnus.winnus.src.searchResult.SearchResultActivityService
import com.winnus.winnus.util.GridSpacingItemDecoration
import com.winnus.winnus.util.PostSubscribeRequest
import com.winnus.winnus.util.SearchFilterRequest
import kotlin.properties.Delegates

class DetailShopActivity: com.winnus.winnus.config.BaseActivity<ActivityDetailShopBinding>(ActivityDetailShopBinding::inflate), DetailShopActivityView {

    lateinit var sortWine: List<Wine>
    lateinit var currentWine: List<Wine>
    var shopId by Delegates.notNull<Int>()
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home ->{finish()}
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.gridviewShopWine.addItemDecoration(GridSpacingItemDecoration(2, 50, false))
        shopId = intent.getIntExtra("shopId",-1)
        val shopImg = intent.getStringExtra("shopImg")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        Glide.with(this)
            .load(shopImg)
            .into(binding.imgShop)
        showLoadingMessageDialog(this,"가게정보를 확인중입니다")
        DetailShopActivityService(this).tryGetDetailShop(shopId)
    }

    override fun onGetShopDetailSuccess(response: DetailShopResponse) {
        Log.d("DetailShopActivity",response.result.toString())
        dismissLoadingMessageDialog()
        if(response.code == 1000){
            binding.tvResultCount.text = "총 " + response.result[0].wineCount+"개"

            sortWine = response.result[1].wineList
            currentWine = sortWine
            val itemList = listOf("신상품순", "가격 낮은 순", "가격 높은 순")

            val spinnerAdapter= object : ArrayAdapter<String>(this, R.layout.spinner_item_shop, itemList) {
                override fun isEnabled(position: Int): Boolean {
                    return true
                }
                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                    return view
                }
            }

            binding.spinnerSort.adapter = spinnerAdapter

            binding.spinnerSort.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when(position){
                        0->{currentWine = sortWine
                            binding.gridviewShopWine.adapter = ShopWinesGridRecyclerAdapter(this@DetailShopActivity,currentWine,this@DetailShopActivity)
                        }
                        1->{currentWine = sortWine.sortedBy { it.price.replace(",","").replace("원","").replace("-","0").toInt() }
                            binding.gridviewShopWine.adapter = ShopWinesGridRecyclerAdapter(this@DetailShopActivity,currentWine,this@DetailShopActivity)
                        }
                        else->{currentWine = sortWine.sortedByDescending { it.price.replace(",","").replace("원","").replace("-","0").toInt() }
                            binding.gridviewShopWine.adapter = ShopWinesGridRecyclerAdapter(this@DetailShopActivity,currentWine,this@DetailShopActivity)
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            binding.gridviewShopWine.layoutManager = GridLayoutManager(this, 2)
            binding.gridviewShopWine.adapter = ShopWinesGridRecyclerAdapter(this, sortWine, this)

            val linearLayoutManager = LinearLayoutManager(this)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.recyclerFoods.layoutManager = linearLayoutManager

            response.result[2].pairingFoodList.add(0,PairingFood(
                "VIEW ALL",
                -1,
                R.color.white.toString()
            ))
            binding.recyclerFoods.adapter = FoodHorizontalAdapter(this,response.result[2].pairingFoodList, this)
        }
    }

    override fun onGetShopDetailFailure(message: String) {
        dismissLoadingMessageDialog()
    }

    override fun onPostSubscribeSuccess(response: BaseResponse) {
    }

    override fun onPostSubscribeFailure(message: String) {
    }

    override fun onGetShopPairingSuccess(response: ShopPairingResponse) {
        dismissLoadingMessageDialog()
        if(response.code == 1000){
            binding.tvResultCount.text = "총 " + response.result[0].wineCount+"개"

            sortWine = response.result[1].pairingWineList
            currentWine = sortWine
            val itemList = listOf("신상품순", "가격 낮은 순", "가격 높은 순")
            val spinnerAdapter= object : ArrayAdapter<String>(this, R.layout.spinner_item_shop, itemList) {
                override fun isEnabled(position: Int): Boolean {
                    return true
                }
                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                    return view
                }
            }

            binding.spinnerSort.adapter = spinnerAdapter
            binding.spinnerSort.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when(position){
                        0->{currentWine = sortWine
                            binding.gridviewShopWine.adapter = ShopWinesGridRecyclerAdapter(this@DetailShopActivity,currentWine,this@DetailShopActivity)
                        }
                        1->{currentWine = sortWine.sortedBy { it.price.replace(",","").replace("원","").replace("-","0").toInt() }
                            binding.gridviewShopWine.adapter = ShopWinesGridRecyclerAdapter(this@DetailShopActivity,currentWine,this@DetailShopActivity)
                        }
                        else->{currentWine = sortWine.sortedByDescending { it.price.replace(",","").replace("원","").replace("-","0").toInt() }
                            binding.gridviewShopWine.adapter = ShopWinesGridRecyclerAdapter(this@DetailShopActivity,currentWine,this@DetailShopActivity)
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            binding.gridviewShopWine.layoutManager = GridLayoutManager(this, 2)
            binding.gridviewShopWine.adapter = ShopWinesGridRecyclerAdapter(this, sortWine, this)
        }
    }

    override fun onGetShopPairingFailure(message: String) {
        dismissLoadingMessageDialog()
    }

    override fun tryPostSubscribe(wineId: Int) {
        val postSubscribeRequest= PostSubscribeRequest(wineId)
        DetailShopActivityService(this).tryPostSubscribe(postSubscribeRequest)
    }

    override fun tryGetShopPairing(wineId: Int) {
        if(wineId == -1){
            DetailShopActivityService(this).tryGetDetailShop(shopId)
        }else{
            showLoadingMessageDialog(this,"와인을 불러오는 중입니다")
            DetailShopActivityService(this).tryGetShopPairingShop(shopId, wineId)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1818) {
            if (resultCode == RESULT_OK) {
                val pos = data?.getIntExtra("pos",-1)
                val sub = data?.getStringExtra("sub")
                if(pos != null){
                    if(pos != -1){
                        if (sub != null) {
                            currentWine[pos].userSubscribeStatus = sub
                            binding.gridviewShopWine.adapter?.notifyItemChanged(pos)

                        }
                    }
                }
            }
        }
    }
}