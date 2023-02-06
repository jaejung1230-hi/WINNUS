package com.winnus.winnus.src.login

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.risingproject.src.emailLogin.LoginService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.winnus.winnus.databinding.ActivityLoginBinding
import com.winnus.winnus.src.login.models.GetSignInRequest
import com.winnus.winnus.src.login.models.GetSignInResponse
import com.winnus.winnus.src.main.MainActivity
import com.winnus.winnus.src.signup.SignUpActivity
import com.winnus.winnus.src.simple.AgreeActivity

class LoginActivity : com.winnus.winnus.config.BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginActivityView {

    internal val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("kakao","로그인 실패- $error")
        } else if (token != null) {
            UserApiClient.instance.me { user, error ->
                val kakaoId = user!!.id
                Log.d("kakao","로그인성공 - 토큰 ${kakaoId}")
                Log.d("kakao","로그인성공 - 토큰 ${user.properties}")

            }
            Log.d("kakao","로그인성공 - 토큰 ${token.accessToken}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.containerActivityLoginBinding.setOnClickListener {
            hideKeyboard()
        }

        //텍스트뷰 밑줄귿기
        binding.tvGoSignup.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.btnLogin.setOnClickListener {
            val getSignInRequest = GetSignInRequest(binding.editId.text.toString(),
                binding.editPw.text.toString()
            )
            LoginService(this).tryGetSignIn(getSignInRequest)
            showLoadingMessageDialog(this,"로그인 시도 중입니다")
        }

        binding.tvGoSignup.setOnClickListener {
            val intent = Intent(this, AgreeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onGetSignInSuccess(response: GetSignInResponse) {
        dismissLoadingMessageDialog()
        if(!response.message.equals("성공")){
            showCustomToast(response.message.toString())
        }
        else{
            val editor = com.winnus.winnus.config.ApplicationClass.sSharedPreferences.edit()
            Log.d("Login",response.result.jwt)
            editor.putString(com.winnus.winnus.config.ApplicationClass.X_ACCESS_TOKEN, response.result.jwt)
            editor.putInt(com.winnus.winnus.config.ApplicationClass.LOG_IN_USER, response.result.userId)
            editor.commit()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onGetSignInFailure(message: String) {
        dismissLoadingMessageDialog()
        showCustomToast(message)
    }
}