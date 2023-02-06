package com.winnus.winnus.src.WineDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.databinding.ActivityWineDetailBinding
import com.winnus.winnus.src.WineDetail.models.GetDetailWineResponse
import com.winnus.winnus.src.WineDetail.models.Reviews
import com.winnus.winnus.src.WineDetail.util.*
import com.winnus.winnus.src.allReview.AllReviewActivity
import com.google.android.material.tabs.TabLayout
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.template.model.*
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.src.WineDetail.models.WineInfo
import com.winnus.winnus.src.searchResult.SearchResultActivityService
import com.winnus.winnus.util.PostReportRequest
import com.winnus.winnus.util.PostSubscribeRequest

import java.lang.Math.round
import java.text.DecimalFormat
import kotlin.properties.Delegates

class DetailWineActivity : com.winnus.winnus.config.BaseActivity<ActivityWineDetailBinding>(ActivityWineDetailBinding::inflate), DetailWineActivityView {
    lateinit var sortedList: List<Reviews>
    var wineId by Delegates.notNull<Int>()
    var pos by Delegates.notNull<Int>()
    lateinit var sub: String

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home ->{finish()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        wineId = intent.getIntExtra("wineId",0)
        pos = intent.getIntExtra("pos",0)
        sub = intent.getStringExtra("sub").toString()
        DetailWineService(this).tryGetDetailWine(wineId)

        showLoadingMessageDialog(this,"와인정보 불러오는 중")

        binding.containerReviewCount.setOnClickListener {
            binding.nestedStickyScrollView.scrollTo(0, binding.linearReview.top-100)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceType")
    override fun onGetDetailWineSuccess(response: GetDetailWineResponse) {
        dismissLoadingMessageDialog()
        sub = response.result[0].wineInfo[0].userSubscribeStatus
        if (sub == "Y"){
            binding.btnSub.setImageResource(R.drawable.ic_winnus_haert_fill_24)
        }else{
            binding.btnSub.setImageResource(R.drawable.ic_winnus_haert_solid_white_24)
        }

        binding.btnSub.setOnClickListener {
            val postSubscribeRequest= PostSubscribeRequest(wineId)
            DetailWineService(this).tryPostSubscribe(postSubscribeRequest)
            if(sub == "Y"){
                sub = "N"
                binding.btnSub.setImageResource(R.drawable.ic_winnus_haert_solid_white_24)
            }else{
                sub = "Y"
                binding.btnSub.setImageResource(R.drawable.ic_winnus_haert_fill_24)
            }

            intent.putExtra("sub",sub)
            intent.putExtra("pos",pos)
            setResult(RESULT_OK, intent)
        }

        binding.btnKakaoLink.setOnClickListener {
            kakaoLink(response.result[0].wineInfo[0])
        }

        binding.nestedStickyScrollView.run {
            header = binding.tabsDetail
            stickListener = { _ ->
                Log.d("LOGGER_TAG", "stickListener")
            }
            freeListener = { _ ->
                Log.d("LOGGER_TAG", "freeListener")
            }
        }

        binding.tabsDetail.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0-> { binding.nestedStickyScrollView.scrollTo(0, binding.linearNote.top-100)}
                    1-> {binding.nestedStickyScrollView.scrollTo(0, binding.linearAroma.top-100)}
                    2-> {binding.nestedStickyScrollView.scrollTo(0, binding.linearReview.top-100)}
                    3-> {binding.nestedStickyScrollView.scrollTo(0, binding.tvKindWineBest.top-150)}

                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.nestedStickyScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            if(scrollY >= binding.tvKindWineBest.top - 50 && scrollY <= binding.tvKindWineBest.top+50){
                binding.tabsDetail.getTabAt(3)?.select()
            }
            else if(scrollY == binding.linearReview.top){
                binding.tabsDetail.getTabAt(2)?.select()
            }
            else if(scrollY == binding.linearAroma.top){
                binding.tabsDetail.getTabAt(1)?.select()
            }
            else if(scrollY == binding.linearNote.top){
                binding.tabsDetail.getTabAt(0)?.select()
            }

        }

        Glide.with(this)
            .load(response.result[0].wineInfo[0].wineImg)
            .error(R.drawable.none_img)
            .into(binding.imgDetailWine)

        if(response.result[0].wineInfo[0].price == "-1"){
            binding.tvDetailWinePrice.text = "가격정보없음"
        }else{
            val t_dec_up = DecimalFormat("#,###")
            var str_change_money_up = t_dec_up.format(response.result[0].wineInfo[0].price.toInt())
            binding.tvDetailWinePrice.text = str_change_money_up
        }
        binding.tvDetailWineQuality.text = response.result[0].wineInfo[0].quantity
        binding.tvDetailWineYear.text = response.result[0].wineInfo[0].productionYear
        binding.tvDetailWineName.text = response.result[0].wineInfo[0].wineName + " ("+ response.result[0].wineInfo[0].inEnglish+")"
        binding.tvDetailWineCountry.text = response.result[0].wineInfo[0].country
        binding.tvDetailWineRegion.text = response.result[0].wineInfo[0].region
        val rate_point2 = round((response.result[0].wineInfo[0].rating*10))/10.0
        binding.ratingDetailReview.rating = rate_point2.toFloat()
        binding.tvDetailWineRatingSmall.text = rate_point2.toString()
        binding.tvDetailWineRating.text = rate_point2.toString()
        binding.tvDetailWineReviewNum.text = "와인리뷰 "+ response.result[0].wineInfo[0].reviewNum.toString()+"건 "
        binding.tvTastingNote.text = response.result[0].wineInfo[0].taste
        binding.tvDetailWineType.text = response.result[0].wineInfo[0].type
        binding.tvDetailWineVariety.text = response.result[0].wineInfo[0].variety
        binding.tvKindWineBest.text = response.result[0].wineInfo[0].type + " 와인 BEST"


        val acidityLevel = listOf<ImageView>(binding.imgAcidityLevel1, binding.imgAcidityLevel2, binding.imgAcidityLevel3 , binding.imgAcidityLevel4, binding.imgAcidityLevel5)
        for(i in 1..acidityLevel.size){
            if(response.result[0].wineInfo[0].acidity == i){
                acidityLevel[i-1].setImageResource(R.color.white)
            }
        }

        val sugarContentLevel = listOf<ImageView>(binding.imgSugarContentLevel1, binding.imgSugarContentLevel2, binding.imgSugarContentLevel3 , binding.imgSugarContentLevel4, binding.imgSugarContentLevel5)
        for(i in 1..sugarContentLevel.size){
            if(response.result[0].wineInfo[0].sweetness == i){
                sugarContentLevel[i-1].setImageResource(R.color.white)
            }
        }

        val bodyLevel = listOf<ImageView>(binding.imgBodyLevel1, binding.imgBodyLevel2, binding.imgBodyLevel3 , binding.imgBodyLevel4, binding.imgBodyLevel5)
        for(i in 1..bodyLevel.size){
            if(response.result[0].wineInfo[0].body == i){
                bodyLevel[i-1].setImageResource(R.color.white)
            }
        }

        val tanninLevel = listOf<ImageView>(binding.imgTanninLevel1, binding.imgTanninLevel2, binding.imgTanninLevel3 , binding.imgTanninLevel4, binding.imgTanninLevel5)
        for(i in 1..tanninLevel.size){
            if(response.result[0].wineInfo[0].tannin == i){
                tanninLevel[i-1].setImageResource(R.color.white)
            }
        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerDetailAroma.layoutManager = linearLayoutManager
        binding.recyclerDetailAroma.adapter = AromaHorizontalAdapter(this, response.result[2].flavorList)

        val linearLayoutManager2 = LinearLayoutManager(this)
        linearLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerDetailFood.layoutManager = linearLayoutManager2
        binding.recyclerDetailFood.adapter = FoodHorizontalAdapter(this, response.result[3].pairingFoodList)

        sortedList = response.result[4].reviews.sortedByDescending { it.createdAt }

        binding.recyclerDetailReviews.layoutManager = LinearLayoutManager(this)
        binding.recyclerDetailReviews.adapter = ReviewAdapter(this, sortedList, this)

        val linearLayoutManager3 = LinearLayoutManager(this)
        linearLayoutManager3.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerBestWine.layoutManager = linearLayoutManager3
        binding.recyclerBestWine.adapter = BestWineHorizontalAdapter(this, response.result[5].bestWineListByType)

        val linearLayoutManager4 = LinearLayoutManager(this)
        linearLayoutManager4.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerSimilarWine.layoutManager = linearLayoutManager4
        binding.recyclerSimilarWine.adapter = SimilarWineHorizontalAdapter(this,response.result[6].similarWineList)

        binding.btnAllReviews.setOnClickListener {
            val intent = Intent(this,AllReviewActivity::class.java)
            intent.putExtra("ratePoint",rate_point2)
            intent.putExtra("wineId",wineId)
            intent.putExtra("wineImg",response.result[0].wineInfo[0].wineImg)
            intent.putExtra("wineName",response.result[0].wineInfo[0].wineName)
            intent.putExtra("country",response.result[0].wineInfo[0].country)
            intent.putExtra("region",response.result[0].wineInfo[0].region)
            startActivity(intent)
        }
    }

    override fun onGetDetailWineInFailure(message: String) {
        dismissLoadingMessageDialog()
    }

    override fun onPostSubscribeSuccess(response: BaseResponse) {
    }

    override fun onPostSubscribeFailure(message: String) {
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortReview(view : View){

        val sort = listOf<TextView>(binding.tvSortReviewNew,binding.tvSortReviewLow,binding.tvSortReviewHigh)
        for(tv in sort){
            tv.typeface = Typeface.DEFAULT
        }
        for(tv in sort){
            if(view.id  == tv.id){
                tv.setTypeface(tv.typeface, Typeface.BOLD)
            }
        }
        when(sort.indexOf(view)){
            0 ->{sortedList = sortedList.sortedByDescending { it.createdAt }}
            1 ->{sortedList = sortedList.sortedBy { it.rating }}
            else ->{sortedList = sortedList.sortedByDescending { it.rating }}
        }
        binding.recyclerDetailReviews.adapter = ReviewAdapter(this, sortedList, this)
    }

    fun kakaoLink(wineInfo: WineInfo) {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = wineInfo.wineName,
                description = wineInfo.country + " / " + wineInfo.region,
                imageUrl = wineInfo.wineImg,
                link = Link(
                    androidExecutionParams = mapOf(
                        "key1" to wineId.toString()
                    )
                )
            ), social = Social(
                commentCount = wineInfo.reviewNum,
            ),
            buttons = listOf(
                Button(
                    "내 취향 와인 만나기",
                    Link(
                        androidExecutionParams = mapOf(
                            "key1" to wineId.toString()
                        )
                    )
                )
            )
        )
        // 피드 메시지 보내기
        LinkClient.instance.defaultTemplate(this, defaultFeed) { linkResult, error ->
            if (error != null) {
                Log.e("카카오링크", "카카오링크 보내기 실패", error)
                showCustomToast(error.toString())
            }
            else if (linkResult != null) {
                Log.d("카카오링크", "카카오링크 보내기 성공 ")
                startActivity(linkResult.intent)

                // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                Log.w("카카오링크", "Warning Msg: ")
                Log.w("카카오링크", "Argument Msg: ")
            }
        }
    }

    override fun onPostReportSuccess(response: BaseResponse) {
        showCustomToast(response.message.toString())
    }

    override fun onPostReportFailure(message: String) {
        showCustomToast(message)
    }

    override fun onPostReport(id: Int, reason: Int) {
        val postReportRequest = PostReportRequest(id,reason)
        DetailWineService(this).tryPostReport(postReportRequest)
    }
}