package com.winnus.winnus.src.filter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.winnus.winnus.R
import com.winnus.winnus.config.ApplicationClass.FilterBoolean.acidityGlobal
import com.winnus.winnus.config.ApplicationClass.FilterBoolean.bodyGlobal
import com.winnus.winnus.config.ApplicationClass.FilterBoolean.sweetnessGlobal
import com.winnus.winnus.config.ApplicationClass.FilterBoolean.tanninGlobal
import com.winnus.winnus.databinding.ActivityFilterBinding
import com.winnus.winnus.src.searchResult.SearchResultActivity
import com.winnus.winnus.util.SearchFilterRequest
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class FilterActivity : com.winnus.winnus.config.BaseActivity<ActivityFilterBinding>(ActivityFilterBinding::inflate) {

    val kindList = listOf<String>("레드","로제","화이트","스파클링")
    val foodList = listOf<String>("소고기","돼지고기","닭고기","생선",
        "과일","치즈","디저트","샐러드","피자","한식","중식","양식","기타")
    val priceList = listOf<String>("1만 원 이하","1 ~ 3만 원","3 ~ 5만 원","5 ~ 7만 원",
        "7 ~ 10만 원","가격무관")
    val bouquetList = listOf<String>("꽃향","과일향","식물향","곡물향",
        "나무향","화학향")

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_close, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //툴바 네비게이션
        setSupportActionBar(binding.toolbarFilter)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP // 아이템크기 유지, multiLine
            flexDirection = FlexDirection.ROW // 가로 방향 정렬
            justifyContent = JustifyContent.FLEX_START // 시작기준 정렬
        }.let {
            binding.recyclerFilterKind.layoutManager = it
            binding.recyclerFilterKind.adapter = FilterRecyclerAdapter(this, kindList, 0)
        }

        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP // 아이템크기 유지, multiLine
            flexDirection = FlexDirection.ROW // 가로 방향 정렬
            justifyContent = JustifyContent.FLEX_START // 시작기준 정렬
        }.let {
            binding.recyclerFilterBouquet.layoutManager = it
            binding.recyclerFilterBouquet.adapter = FilterRecyclerAdapter(this, bouquetList, 1)
        }

        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP // 아이템크기 유지, multiLine
            flexDirection = FlexDirection.ROW // 가로 방향 정렬
            justifyContent = JustifyContent.FLEX_START // 시작기준 정렬
        }.let {
            binding.recyclerFilterFood.layoutManager = it
            binding.recyclerFilterFood.adapter = FilterRecyclerAdapter(this, foodList, 2)
        }

        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP // 아이템크기 유지, multiLine
            flexDirection = FlexDirection.ROW // 가로 방향 정렬
            justifyContent = JustifyContent.FLEX_START // 시작기준 정렬
        }.let {
            binding.recyclerFilterPrice.layoutManager = it
            binding.recyclerFilterPrice.adapter = FilterRecyclerRadioAdapter(this, priceList, 3)
        }

        binding.tvFilterKind.setOnClickListener {
            if(binding.recyclerFilterKind.visibility == View.VISIBLE){
                binding.recyclerFilterKind.visibility = View.GONE
                binding.tvFilterKind.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_winnus_plus_24, 0)
            }else{
                binding.recyclerFilterKind.visibility = View.VISIBLE
                binding.tvFilterKind.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_winnus_minus_24, 0)
            }
        }

        binding.tvFilterTaste.setOnClickListener {
            if(binding.linearFilterTaste.visibility == View.VISIBLE){
                binding.linearFilterTaste.visibility = View.GONE
                binding.tvFilterTaste.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_winnus_plus_24, 0)
            }else{
                binding.linearFilterTaste.visibility = View.VISIBLE
                binding.tvFilterTaste.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_winnus_minus_24, 0)
            }
        }

        binding.tvFilterBouquet.setOnClickListener {
            if(binding.recyclerFilterBouquet.visibility == View.VISIBLE){
                binding.recyclerFilterBouquet.visibility = View.GONE
                binding.tvFilterBouquet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_winnus_plus_24, 0)
            }else{
                binding.recyclerFilterBouquet.visibility = View.VISIBLE
                binding.tvFilterBouquet.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_winnus_minus_24, 0)
            }
        }

        binding.tvFilterFood.setOnClickListener {
            if(binding.recyclerFilterFood.visibility == View.VISIBLE){
                binding.recyclerFilterFood.visibility = View.GONE
                binding.tvFilterFood.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_winnus_plus_24, 0)
            }else{
                binding.recyclerFilterFood.visibility = View.VISIBLE
                binding.tvFilterFood.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_winnus_minus_24, 0)
            }
        }

        binding.tvFilterPrice.setOnClickListener {
            if(binding.linearFilterPrice.visibility == View.VISIBLE){
                binding.linearFilterPrice.visibility = View.GONE
                binding.tvFilterPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_winnus_plus_24, 0)
            }else{
                binding.linearFilterPrice.visibility = View.VISIBLE
                binding.tvFilterPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_winnus_minus_24, 0)
            }
        }

        binding.seekBarAcidity.progress = acidityGlobal
        binding.seekBarBody.progress = bodyGlobal
        binding.seekBarSugarContent.progress = sweetnessGlobal
        binding.seekBarTannin.progress = tanninGlobal

        binding.btnFilterReset.setOnClickListener {
            com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr = arrayListOf(arrayListOf(false,false,false,false), arrayListOf(false,false,false,false,false,false),
                arrayListOf(false,false,false,false,false,false,false,false,false,false,false,false), arrayListOf(false,false,false,false,false,false))

            binding.seekBarAcidity.progress = 0
            binding.seekBarBody.progress = 0
            binding.seekBarSugarContent.progress = 0
            binding.seekBarTannin.progress = 0

            binding.recyclerFilterPrice.adapter?.notifyDataSetChanged()
            binding.recyclerFilterFood.adapter?.notifyDataSetChanged()
            binding.recyclerFilterBouquet.adapter?.notifyDataSetChanged()
            binding.recyclerFilterKind.adapter?.notifyDataSetChanged()
        }

        binding.btnFilterSearch.setOnClickListener{

            com.winnus.winnus.config.ApplicationClass.FilterBoolean.sweetnessGlobal = binding.seekBarSugarContent.progress
            com.winnus.winnus.config.ApplicationClass.FilterBoolean.acidityGlobal = binding.seekBarAcidity.progress
            com.winnus.winnus.config.ApplicationClass.FilterBoolean.bodyGlobal = binding.seekBarBody.progress
            com.winnus.winnus.config.ApplicationClass.FilterBoolean.tanninGlobal = binding.seekBarTannin.progress

            val type = arrayListOf<Int>()
            var sweetness : Int = 0
            var acidity : Int = 0
            var body : Int = 0
            var tannin : Int = 0
            val flavors = arrayListOf<Int>()
            val foods = arrayListOf<Int>()
            var price : String? = null
            for(i in 0 until com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[0].size){
                if(com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[0][i]){
                    type.add((i+1))
                }
            }
            sweetness = binding.seekBarSugarContent.progress
            acidity = binding.seekBarAcidity.progress
            body = binding.seekBarBody.progress
            tannin = binding.seekBarTannin.progress

            for(i in 0 until com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[1].size){
                if(com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[1][i]){
                    flavors.add((i+1))
                }
            }

            for(i in 0 until com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[2].size){
                if(com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[2][i]){
                    foods.add((i+1))
                }
            }



            if(binding.editPriceMin.text.isNotEmpty() && binding.editPriceMax.text.isNotEmpty()){
                price="${binding.editPriceMin.text}~${binding.editPriceMax.text}"
            }else{
              for(i in 0 until com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[3].size){
                  if(com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr[3][i]){
                      when(i){
                          0->{price="0~10000"}
                          1->{price="10000~30000"}
                          2->{price="30000~50000"}
                          3->{price="50000~70000"}
                          4->{price="70000~100000"}
                          else->{price=null}
                      }
                      break
                  }
              }
            }

            val searchFilterRequest = SearchFilterRequest(
                null,
                if(type.size !=0){type}else{null},
                if(sweetness !=0){sweetness}else{null},
                if(acidity !=0){acidity}else{null},
                if(body !=0){body}else{null},
                if(tannin !=0){tannin}else{null},
                if(flavors.size !=0){flavors}else{null},
                if(foods.size !=0){foods}else{null},
                price
            )

            if(searchFilterRequest.type == null && searchFilterRequest.sweetness == null && searchFilterRequest.acidity == null
                && searchFilterRequest.body == null && searchFilterRequest.tannin == null && searchFilterRequest.flavors == null
                && searchFilterRequest.foods == null && searchFilterRequest.price == null){
                showCustomToast("필터 정보를 입력해주세요")
            }else{
                val intent = Intent(this, SearchResultActivity::class.java)
                intent.putExtra("isFilter",1)
                intent.putExtra("filter",searchFilterRequest)
                startActivity(intent)
            }



        }

    }
}