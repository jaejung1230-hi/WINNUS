package com.winnus.winnus.src.simple

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.databinding.ActivityVocBinding

class VocActivity : com.winnus.winnus.config.BaseActivity<ActivityVocBinding>(ActivityVocBinding::inflate) {

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

        binding.btnPostVoc.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/yNB8iRGzMwQBQ5JYA"))
            startActivity(intent)
        }
    }
}