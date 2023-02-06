package com.winnus.winnus.src.writeReview

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.winnus.winnus.R
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.databinding.ActivityWriteReviewBinding
import com.winnus.winnus.src.WineDetail.util.ReviewAdapter
import com.winnus.winnus.src.writeReview.model.PostReviewRequest
import com.winnus.winnus.src.writeReview.util.KeyWordAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class WriteReviewActivity : com.winnus.winnus.config.BaseActivity<ActivityWriteReviewBinding>(ActivityWriteReviewBinding::inflate), WriteReviewActivityView {
    val key = ArrayList<Map<String,String>>()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home ->{finish()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.containerActivityWriteReviewBinding.setOnClickListener {
            hideKeyboard()
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val wineId = intent.getIntExtra("wineId",0)
        val wineImg = intent.getStringExtra("wineImg")
        val wineName = intent.getStringExtra("wineName")
        val country = intent.getStringExtra("country")
        val region = intent.getStringExtra("region")

        Glide.with(this)
            .load(wineImg)
            .error(R.drawable.temp_wine)
            .into(binding.imgSelectedItem)
        binding.tvSelectedItemName.text = wineName
        binding.tvSelectedItemContury.text = country
        binding.tvSelectedItemRegion.text = region

        binding.ratingbarPoint.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating<1.0f){
                binding.ratingbarPoint.rating = 1.0f
            }
        }

        binding.editComment.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvEditLength.text = s?.length.toString()
            }
        })

        binding.btnUploadReview.setOnClickListener {
            if(checkRate() || checkLength() ){
                showCustomToast("필수 입력사항을 확인해주세요")
            }else{
                if(key.size != 0){
                    val postReviewRequest = PostReviewRequest(wineId,binding.ratingbarPoint.rating,binding.editComment.text.toString(),key)
                    WriteReviewActivityService(this).tryPostReview(postReviewRequest)
                }else{
                    val postReviewRequest = PostReviewRequest(wineId,binding.ratingbarPoint.rating,binding.editComment.text.toString(),null)
                    WriteReviewActivityService(this).tryPostReview(postReviewRequest)
                }
            }
        }

        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP // 아이템크기 유지, multiLine
            flexDirection = FlexDirection.ROW // 가로 방향 정렬
            justifyContent = JustifyContent.FLEX_START // 시작기준 정렬
        }.let {

            binding.recyclerKeyword.layoutManager = it
            binding.recyclerKeyword.adapter = KeyWordAdapter(this, key, this)
        }


        binding.editKeyword.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.editKeyword.windowToken, 0)
                    key.add(mapOf("tag" to binding.editKeyword.text.toString()))
                    binding.editKeyword.text = null
                    binding.recyclerKeyword.adapter!!.notifyDataSetChanged()
                    return true
                }
                return false
            }
        })

    }

    fun checkRate(): Boolean {
        return binding.ratingbarPoint.rating == 0.0f
    }

    fun checkLength(): Boolean {
        return binding.editComment.text.length < 20
    }

    override fun onPostReviewSuccess(response: BaseResponse) {
        showCustomToast(response.message.toString())
        if(response.code == 1004){
            finish()
        }
    }

    override fun onPostReviewFailure(message: String) {
        showCustomToast(message)
    }

    override fun getSelectedTag(item: Int) {
        key.removeAt(item)
        binding.recyclerKeyword.adapter!!.notifyDataSetChanged()
    }
}