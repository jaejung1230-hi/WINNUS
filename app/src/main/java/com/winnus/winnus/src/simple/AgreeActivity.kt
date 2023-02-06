package com.winnus.winnus.src.simple

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.databinding.ActivityAgreeBinding
import com.winnus.winnus.databinding.ActivityVocBinding
import com.winnus.winnus.src.signup.SignUpActivity

class AgreeActivity : com.winnus.winnus.config.BaseActivity<ActivityAgreeBinding>(ActivityAgreeBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvSigninTemp2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://makeus-challenge.notion.site/92dfc457279b42b18b3aa408237bd51e"))
            startActivity(intent)
        }

        binding.tvSigninTemp3.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://makeus-challenge.notion.site/92dfc457279b42b18b3aa408237bd51e"))
            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            if(binding.checkboxAge.isChecked && binding.checkboxProfile.isChecked && binding.checkboxPromise.isChecked){
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }else{
                showCustomToast("이용 약관에 동의해주세요")
            }
        }
    }
}