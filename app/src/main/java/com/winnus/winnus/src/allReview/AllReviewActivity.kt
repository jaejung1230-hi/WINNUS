package com.winnus.winnus.src.allReview

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.databinding.ActivityAllReviewsBinding
import com.winnus.winnus.src.allReview.model.AllReviewResponse
import com.winnus.winnus.src.allReview.model.WineReview
import com.winnus.winnus.src.allReview.util.AllReviewAdapter
import com.winnus.winnus.src.writeReview.WriteReviewActivity
import com.winnus.winnus.util.PostReportRequest

class AllReviewActivity : BaseActivity<ActivityAllReviewsBinding>(ActivityAllReviewsBinding::inflate), AllReviewActivityView {
    lateinit var sortedList: List<WineReview>

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

        binding.ratingDetailReview.rating = intent.getDoubleExtra("ratePoint",0.0).toFloat()
        binding.tvDetailWineRatingSmall.text = intent.getDoubleExtra("ratePoint",0.0).toFloat().toString()

        val wineId = intent.getIntExtra("wineId",0)
        val wineImg = intent.getStringExtra("wineImg")
        val wineName = intent.getStringExtra("wineName")
        val country = intent.getStringExtra("country")
        val region = intent.getStringExtra("region")

        binding.btnWriteReview.setOnClickListener {
            val intent = Intent(this, WriteReviewActivity::class.java)
            intent.putExtra("wineId",wineId)
            intent.putExtra("wineImg",wineImg)
            intent.putExtra("wineName",wineName)
            intent.putExtra("country",country)
            intent.putExtra("region",region)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        AllReviewActivityService(this).tryGetAllReview(intent.getIntExtra("wineId",0))
    }

    override fun onGetAllReviewSuccess(response: AllReviewResponse) {

        if(response.result.wineReviews.isNotEmpty()){
            binding.recyclerDetailReviews.layoutManager = LinearLayoutManager(this)
            sortedList = response.result.wineReviews.sortedByDescending { it.review.createdAt }
            binding.recyclerDetailReviews.adapter = AllReviewAdapter(this, sortedList, this)
        }

    }

    override fun onGetAllReviewFailure(message: String) {
        showCustomToast(message)
    }

    override fun onPostReportSuccess(response: BaseResponse) {
        showCustomToast(response.message.toString())
    }

    override fun onPostReportFailure(message: String) {
        showCustomToast(message)
    }

    override fun onPostReport(id: Int, reason: Int) {
        val postReportRequest = PostReportRequest(id,reason)
        AllReviewActivityService(this).tryPostReport(postReportRequest)
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
            0 ->{sortedList = sortedList.sortedByDescending { it.review.createdAt }}
            1 ->{sortedList = sortedList.sortedBy { it.review.rating }}
            else ->{sortedList = sortedList.sortedByDescending { it.review.rating }}
        }
        binding.recyclerDetailReviews.adapter = AllReviewAdapter(this, sortedList, this)
    }
}