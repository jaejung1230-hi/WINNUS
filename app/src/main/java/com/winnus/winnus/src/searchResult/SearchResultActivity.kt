package com.winnus.winnus.src.searchResult

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.winnus.winnus.R
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.databinding.ActivitySearchResultBinding
import com.winnus.winnus.src.filter.SubFilterActivity
import com.winnus.winnus.src.searchResult.model.FilteringRe
import com.winnus.winnus.src.searchResult.model.RetrieveWineRes
import com.winnus.winnus.src.searchResult.model.SearchFilterResponse
import com.winnus.winnus.src.searchResult.model.SearchResultResponse
import com.winnus.winnus.util.PostSubscribeRequest
import com.winnus.winnus.util.SearchFilterRequest
import java.lang.Integer.min
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import com.winnus.winnus.src.searchResult.util.*
import com.winnus.winnus.util.GridSpacingItemDecoration
import android.app.Activity
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class SearchResultActivity: com.winnus.winnus.config.BaseActivity<ActivitySearchResultBinding>(ActivitySearchResultBinding::inflate),SearchResultActivityView {
    lateinit var sortWine: List<RetrieveWineRes>
    lateinit var sortFilterWine: ArrayList<FilteringRe>
    var isFilter = false
    var keyName : String? = null
    var pageNum : Int = 1
    var searchFilterRequest: SearchFilterRequest? = null
    var orderBy = " "
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
        binding.gridviewSearchResultWine.addItemDecoration(GridSpacingItemDecoration(2, 50, false))

        val itemList = listOf("신상품순", "가격 낮은 순", "가격 높은 순")
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, itemList)
        binding.spinnerSort.adapter = adapter

        isFilter = intent.getIntExtra("isFilter",0) == 1

        if(isFilter){
            searchFilterRequest = intent.getParcelableExtra<SearchFilterRequest>("filter")
            Log.d("filter", searchFilterRequest.toString())
            if (searchFilterRequest != null) {
                SearchResultActivityService(this).tryGetFilterResult(searchFilterRequest!!, pageNum, orderBy)
                showLoadingMessageDialog(this,"와인 검색 중입니다..")
            }
        }else{
            keyName = intent.getStringExtra("text")
            binding.tvSearchKeyName.text = "$keyName 검색 결과"
            showLoadingMessageDialog(this,"와인 검색 중입니다..")
            intent.getStringExtra("text")?.let { SearchResultActivityService(this).tryGetSearchResult(it) }
        }

        binding.imgFilter.setOnClickListener {
            val intent = Intent(this,SubFilterActivity::class.java)
            startActivityForResult(intent, 100)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.imgFilter.setImageResource(R.drawable.ic_winnus_filter_solid_24)
        for(i in com.winnus.winnus.config.ApplicationClass.FilterBoolean.arr){
            for(j in i){
                if(j){
                    binding.imgFilter.setImageResource(R.drawable.ic_winnus_filter_fill_24)
                    break
                }
            }
        }
        if( com.winnus.winnus.config.ApplicationClass.FilterBoolean.sweetnessGlobal != 0 ||
            com.winnus.winnus.config.ApplicationClass.FilterBoolean.acidityGlobal != 0 ||
            com.winnus.winnus.config.ApplicationClass.FilterBoolean.bodyGlobal != 0 ||
            com.winnus.winnus.config.ApplicationClass.FilterBoolean.tanninGlobal != 0 ){
            binding.imgFilter.setImageResource(R.drawable.ic_winnus_filter_fill_24)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onGetSearchResultSuccess(response: SearchResultResponse) {
        dismissLoadingMessageDialog()
        if(response.isSuccess){
            if(response.result[0].wineCount[0].wineNum != 0){
                haveResult()
                val linearLayoutManager = LinearLayoutManager(this)
                linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                binding.recyclerMostSimilar.layoutManager = linearLayoutManager
                binding.recyclerMostSimilar.adapter = SearchResultWineHorizontalAdapter(this, response.result[1].retrieveWineRes.subList(0,min(4,response.result[1].retrieveWineRes.size)),this)
                sortWine = response.result[1].retrieveWineRes

                binding.gridviewSearchResultWine.layoutManager = GridLayoutManager(this, 2)
                binding.gridviewSearchResultWine.adapter = SearchResultWineGridRecyclerAdapter(this,sortWine,this)


                binding.spinnerSort.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        when(position){
                            0->{binding.gridviewSearchResultWine.adapter = SearchResultWineGridRecyclerAdapter(this@SearchResultActivity,sortWine,this@SearchResultActivity)}
                            1->{binding.gridviewSearchResultWine.adapter = SearchResultWineGridRecyclerAdapter(this@SearchResultActivity,sortWine.sortedBy { it.price.replace(",","").replace("원","").replace("-","0").toInt() },this@SearchResultActivity)}
                            else->{binding.gridviewSearchResultWine.adapter = SearchResultWineGridRecyclerAdapter(this@SearchResultActivity,sortWine.sortedByDescending { it.price.replace(",","").replace("원","").replace("-","0").toInt() },this@SearchResultActivity)}
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }else{
                noneResult()
            }
        }else{
            noneResult()
        }



    }

    override fun onGetSearchResultFailure(message: String) {
        dismissLoadingMessageDialog()
        showCustomToast(message)
    }

    var filteringRe = arrayListOf<FilteringRe>()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onGetFilterResultSuccess(response: SearchFilterResponse) {
        dismissLoadingMessageDialog()
        if(binding.frameProgress.visibility == View.VISIBLE){
            binding.frameProgress.visibility = View.GONE
        }

        if(response.result.filteringRes.isNotEmpty()){
            haveResult()
            binding.nestedscrollviewResult.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    pageNum += 1
                    SearchResultActivityService(this).tryGetFilterResult(searchFilterRequest!!, pageNum, orderBy)
                    binding.frameProgress.visibility = View.VISIBLE
                }
            })

            if(pageNum == 1){
                filteringRe = arrayListOf<FilteringRe>()
                for(i in 0 until response.result.filteringRes.size){
                    filteringRe.add(response.result.filteringRes[i][0])
                }

                Log.d("SearchResultActivity","최초")
                val linearLayoutManager = LinearLayoutManager(this)
                linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                binding.recyclerMostSimilar.layoutManager = linearLayoutManager
                val resultWineHorizontal = filteringRe.subList(0,min(4,filteringRe.size))
                binding.recyclerMostSimilar.adapter = SearchFilterResultWineHorizontalAdapter(this, resultWineHorizontal,this)
                sortFilterWine = filteringRe

                binding.gridviewSearchResultWine.layoutManager = GridLayoutManager(this, 2)
                binding.gridviewSearchResultWine.adapter = SearchFilterWineGridRecyclerAdapter(this,sortFilterWine,this)


                binding.spinnerSort.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        var orderByNew : String = ""
                        when(position){
                            0->{orderByNew = " " }
                            1->{orderByNew = "low" }
                            else->{orderByNew = "high" }
                        }
                        if(orderByNew != orderBy){
                            orderBy = orderByNew
                            pageNum = 1
                            searchFilterRequest?.let {
                                SearchResultActivityService(this@SearchResultActivity).tryGetFilterResult(it, pageNum, orderBy)
                                showLoadingMessageDialog(this@SearchResultActivity,"와인 검색 중입니다..")
                            }
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }else{

                val filteringRe2 = arrayListOf<FilteringRe>()
                for(i in 0 until response.result.filteringRes.size){
                    filteringRe.add(response.result.filteringRes[i][0])
                    filteringRe2.add(response.result.filteringRes[i][0])
                }
                sortFilterWine = filteringRe

                val resultWineHorizontal = filteringRe.subList(0,min(4,filteringRe.size))
                binding.recyclerMostSimilar.adapter = SearchFilterResultWineHorizontalAdapter(this, resultWineHorizontal,this)
                sortFilterWine = filteringRe

                binding.gridviewSearchResultWine.adapter = SearchFilterWineGridRecyclerAdapter(this,sortFilterWine,this)
            }

        }else{
            if(pageNum == 1){
                noneResult()
            }
        }
    }

    override fun onGetFilterResultFailure(message: String) {
        dismissLoadingMessageDialog()
        if(binding.frameProgress.visibility == View.VISIBLE){
            binding.frameProgress.visibility = View.GONE
        }
        showCustomToast(message)
    }

    override fun onPostSubscribeSuccess(response: BaseResponse) {

    }

    override fun onPostSubscribeFailure(message: String) {
        showCustomToast(message)
    }

    override fun tryPostSubscribe(wineId: Int) {
        val postSubscribeRequest= PostSubscribeRequest(wineId)
        SearchResultActivityService(this).tryPostSubscribe(postSubscribeRequest)
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
                            sortFilterWine[pos].userSubscribeStatus = sub
                            binding.gridviewSearchResultWine.adapter?.notifyItemChanged(pos)
                            if(pos < 4){
                                binding.recyclerMostSimilar.adapter?.notifyItemChanged(pos)
                            }
                        }
                    }
                }
            }
        }
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                val searchFilterRequest = data?.getParcelableExtra<SearchFilterRequest>("filter")
                if (searchFilterRequest != null) {
                    pageNum = 1
                    searchFilterRequest.keyword = keyName
                    this.searchFilterRequest = searchFilterRequest
                    SearchResultActivityService(this).tryGetFilterResult(searchFilterRequest, pageNum, orderBy)
                    showLoadingMessageDialog(this,"와인 검색 중입니다..")
                }
            }
        }
    }

    fun noneResult(){
        binding.nestedscrollviewResult.visibility = View.GONE
        binding.constraintNoneSearchResult.visibility = View.VISIBLE
    }

    fun haveResult(){
        binding.nestedscrollviewResult.visibility = View.VISIBLE
        binding.constraintNoneSearchResult.visibility = View.GONE
    }
}