package com.winnus.winnus.src.bookMarkWine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.databinding.ActivitySubscribeWinesBinding
import com.winnus.winnus.src.ShopDetail.model.Wine
import com.winnus.winnus.src.bookMarkWine.model.SebscribesWinesResponse
import com.winnus.winnus.src.bookMarkWine.model.Subscribe
import com.winnus.winnus.src.bookMarkWine.util.SubscribesWinesGridRecyclerAdapter
import com.winnus.winnus.src.bookMarkWine.util.SubscribesWinesGridViewAdapter
import com.winnus.winnus.util.GridSpacingItemDecoration
import com.winnus.winnus.util.PostSubscribeRequest

class SubscribesWinesActivity : com.winnus.winnus.config.BaseActivity<ActivitySubscribeWinesBinding>(ActivitySubscribeWinesBinding::inflate), SubscribesWinesActivityView {
    lateinit var sortWine: List<Subscribe>

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
        binding.gridviewSubscribes.addItemDecoration(GridSpacingItemDecoration(2, 50, false))
        SubscribesWinesActivityService(this).tryGetSubscribesWines()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onGetAllReviewSuccess(response: SebscribesWinesResponse) {
        if(response.code==1000){
            Log.d("SubscribesWinesActivity",response.result.toString())
            sortWine = response.result[1].subscribeList
            binding.gridviewSubscribes.layoutManager = GridLayoutManager(this, 2)
            binding.gridviewSubscribes.adapter = SubscribesWinesGridRecyclerAdapter(this, sortWine, this)
        }
    }

    override fun onGetAllReviewFailure(message: String) {

    }

    override fun onPostSubscribeSuccess(response: BaseResponse) {

    }

    override fun onPostSubscribeFailure(message: String) {

    }

    override fun tryPostSubscribe(wineId: Int) {
        val postSubscribeRequest= PostSubscribeRequest(wineId)
        SubscribesWinesActivityService(this).tryPostSubscribe(postSubscribeRequest)
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
                            sortWine[pos].userSubscribeStatus = sub
                            binding.gridviewSubscribes.adapter?.notifyItemChanged(pos)

                        }
                    }
                }
            }
        }
    }
}