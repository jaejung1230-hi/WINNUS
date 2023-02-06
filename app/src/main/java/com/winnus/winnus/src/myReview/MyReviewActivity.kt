package com.winnus.winnus.src.myReview

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.databinding.ActivityMyReviewsBinding
import com.winnus.winnus.src.myReview.model.MyReviewResponse
import com.winnus.winnus.src.myReview.model.UserReview
import com.winnus.winnus.src.myReview.util.MyReviewAdapter

class MyReviewActivity : com.winnus.winnus.config.BaseActivity<ActivityMyReviewsBinding>(ActivityMyReviewsBinding::inflate), MyReviewActivityView {

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

    }

    override fun onStart() {
        super.onStart()
        MyReviewActivityService(this).tryGetAllReview()
    }

    override fun onGetAllReviewSuccess(response: MyReviewResponse) {
        if(response.code == 1000){
            binding.recyclerDetailReviews.layoutManager = LinearLayoutManager(this)
            binding.recyclerDetailReviews.adapter = MyReviewAdapter(this, response.result[1].userReviews as ArrayList<UserReview>, this)
        }

    }


    override fun onGetAllReviewFailure(message: String) {
        showCustomToast(message)
    }

    override fun onDeleteReviewSuccess(response: BaseResponse) {}

    override fun onDeleteReviewFailure(message: String) {}

    override fun onTryDelete(id: Int) {
        MyReviewActivityService(this).tryPatchReview(id)
    }
}