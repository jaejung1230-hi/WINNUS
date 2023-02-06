package com.winnus.winnus.src.simple

import android.os.Bundle
import android.view.MenuItem
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.databinding.ActivityIntroduceBinding
import com.winnus.winnus.databinding.ActivityNoticeBinding
import com.winnus.winnus.databinding.ActivityShopSearchResultBinding
import com.winnus.winnus.databinding.ActivityVocBinding

class IntroduceActivity : com.winnus.winnus.config.BaseActivity<ActivityIntroduceBinding>(ActivityIntroduceBinding::inflate) {
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
}