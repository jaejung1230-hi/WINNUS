package com.winnus.winnus.src.shopResult

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.databinding.ActivityShopSearchResultBinding
import com.winnus.winnus.src.shopResult.models.ShopSearchResponse
import kotlin.properties.Delegates


class ShopResultActivity: com.winnus.winnus.config.BaseActivity<ActivityShopSearchResultBinding>(ActivityShopSearchResultBinding::inflate), ShopResultActivityView{
    var locList = mutableListOf<String>("전체","서울","성남", "부산", "대구", "대전", "울산", "광주", "인천")
    var locListAll = mutableListOf<String>("전체")
    var locListSeoul = mutableListOf<String>("전체","종로구", "중구", "용산구", "성동구", "광진구", "중랑구","성북구","동대문구","강북구","도봉구",
        "노원구","은평구","서대문구","마포구","양천구","강서구","구로구","금천구", "영등포구", "동작구", "관악구", "서초구",
        "강남구","송파구","강동구")
    var locListSungnam = mutableListOf<String>("전체", "수정구","중원구","분당구")
    var locListBusan = mutableListOf<String>("전체","중구", "동구", "서구", "영도구", "부산진구", "동래구", "연제구", "금정구", "북구", "사상구", "사하구", "강서구", "남구", "해운대구", "수영구", "기장군")
    var locListDaegu = mutableListOf<String>("전체","서구", "북구", "동구", "달서구", "중구", "수성구", "남구", "달성군")
    var locListDaejun = mutableListOf<String>("전체","중구", "동구", "서구", "유성구", "대덕구")
    var locListUlsan = mutableListOf<String>("전체","북구", "남구", "둥구", "동구", "울주군")
    var locListGwangju = mutableListOf<String>("전체","동구", "남구", "서구", "북구", "광산구")
    var locListInchon = mutableListOf<String>("전체","중구", "동구", "미추홀구", "남동구", "연수구", "부평구", "계양구", "서구", "강화군", "옹진군")
    var locationBig by Delegates.notNull<Int>()
    var locationSmall by Delegates.notNull<Int>()
    lateinit var text : String

    var isPostBack = false

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home ->{finish()}
        }
        return super.onOptionsItemSelected(item)
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        locationBig = intent.getIntExtra("LocationBig",0)
        locationSmall = intent.getIntExtra("LocationSmall",0)
        text = intent.getStringExtra("text").toString()

        if(text != ""){
            binding.tvShopResult.text = text + " 검색 결과"
        }

        binding.spinnerLocationBig.adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, locList)
        when(locationBig){
            0->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locListAll)}
            1->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locListSeoul)}
            2->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locListSungnam)}
            3->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locListBusan)}
            4->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locListDaegu)}
            5->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locListDaejun)}
            6->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locListUlsan)}
            7->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locListGwangju)}
            8->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locListInchon)}
        }


        binding.spinnerLocationBig.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this@ShopResultActivity, android.R.layout.simple_spinner_dropdown_item, locListAll)}
                    1->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this@ShopResultActivity, android.R.layout.simple_spinner_dropdown_item, locListSeoul)}
                    2->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this@ShopResultActivity, android.R.layout.simple_spinner_dropdown_item, locListSungnam)}
                    3->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this@ShopResultActivity, android.R.layout.simple_spinner_dropdown_item, locListBusan)}
                    4->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this@ShopResultActivity, android.R.layout.simple_spinner_dropdown_item, locListDaegu)}
                    5->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this@ShopResultActivity, android.R.layout.simple_spinner_dropdown_item, locListDaejun)}
                    6->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this@ShopResultActivity, android.R.layout.simple_spinner_dropdown_item, locListUlsan)}
                    7->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this@ShopResultActivity, android.R.layout.simple_spinner_dropdown_item, locListGwangju)}
                    8->{binding.spinnerLocationSmall.adapter = ArrayAdapter(this@ShopResultActivity, android.R.layout.simple_spinner_dropdown_item, locListInchon)}
                }
                if(isPostBack){
                    searchRefresh()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.spinnerLocationSmall.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(isPostBack){
                    searchRefresh()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    override fun onStart() {
        super.onStart()
        var area : String? = null
        isPostBack = false

        binding.spinnerLocationBig.setSelection(locationBig)
        binding.spinnerLocationSmall.setSelection(locationSmall)

        if(locationBig == 0){
            area = ""
        }else if(locationSmall == 0){
            area = binding.spinnerLocationBig.selectedItem as String?
        }else{
            area = binding.spinnerLocationBig.selectedItem as String? + " " + binding.spinnerLocationSmall.selectedItem.toString()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            binding.spinnerLocationBig.setSelection(locationBig)
            binding.spinnerLocationSmall.setSelection(locationSmall)
            showLoadingMessageDialog(this,"상점 정보를 검색 중입니다")
            ShopResultActivityService(this).tryGetShop(text, area.toString())
        }, 100)
    }

    override fun onGetShopSuccess(response: ShopSearchResponse) {
        Log.d("ShopResultActivity",response.toString())
        dismissLoadingMessageDialog()
        isPostBack = true
        if(response.code == 1000){
            if(response.result[1].shopList.isNotEmpty()){
                haveResult()
                binding.tvResultCount.text = "총 " +response.result[0].wineShopCount.toString() +"개"
                binding.recyclerShop.layoutManager = LinearLayoutManager(this)
                binding.recyclerShop.adapter = ShopAdapter(this,response.result[1].shopList)
            }else{
                noneResult()
            }
        }else{
            noneResult()
        }

    }

    override fun onGetShopFailure(message: String) {
        Log.d("ShopResultActivity",message)
        dismissLoadingMessageDialog()
        noneResult()
    }

    fun noneResult(){
        binding.constraintCount.visibility = View.GONE
        binding.recyclerShop.visibility = View.GONE
        binding.constraintNoneSearchResult.visibility = View.VISIBLE
    }

    fun haveResult(){
        binding.constraintCount.visibility = View.VISIBLE
        binding.recyclerShop.visibility = View.VISIBLE
        binding.constraintNoneSearchResult.visibility = View.GONE
    }

    fun searchRefresh(){
        var area : String? = null
        if(binding.spinnerLocationBig.selectedItemPosition == 0){
            area = ""
        }else if(binding.spinnerLocationSmall.selectedItemPosition == 0){
            area = binding.spinnerLocationBig.selectedItem as String?
        }else{
            area = binding.spinnerLocationBig.selectedItem as String? + " " + binding.spinnerLocationSmall.selectedItem.toString()
        }
        //showLoadingMessageDialog(this,"상점 정보를 검색 중입니다")
        ShopResultActivityService(this).tryGetShop(text, area.toString())
    }

}