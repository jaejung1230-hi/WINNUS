package com.winnus.winnus.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.databinding.ActivitySplashBinding
import com.winnus.winnus.src.login.LoginActivity
import com.winnus.winnus.src.main.MainActivity
import com.winnus.winnus.src.splash.models.PostLoginCheckResponse

class SplashActivity : com.winnus.winnus.config.BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), SplashActivityView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            SplashService(this).tryPostLoginCheck()
        }, 1500)
    }

    override fun onPostLoginCheckSuccess(response: PostLoginCheckResponse) {
        if (response.code != 1001) {
            startActivity(Intent(this, LoginActivity::class.java))
        }else{
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }

    override fun onPostLoginCheckFailure(message: String) {
        finish()
    }
}