package com.winnus.winnus.src.signup

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import com.winnus.winnus.R
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.databinding.ActivityLoginBinding
import com.winnus.winnus.databinding.ActivitySignupBinding
import com.winnus.winnus.src.filter.FilterActivity
import com.winnus.winnus.src.login.LoginActivity
import com.winnus.winnus.src.main.MainActivity
import com.winnus.winnus.src.signup.models.GetCodeCheckRequest
import com.winnus.winnus.src.signup.models.GetCodeRequest
import com.winnus.winnus.src.signup.models.GetSignUpRequest
import com.winnus.winnus.src.signup.models.GetSignUpResponse
import java.util.regex.Pattern

class SignUpActivity : com.winnus.winnus.config.BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate), SignUpActivityView {
    var pwdValidation = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,15}\$"
    var telValidation = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})\\d{4}\$"

    var timer1 : CountDownTimer = object : CountDownTimer(180000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.editCode.isEnabled = true
            binding.btnCodeSummit.isClickable = true
            var seconds = (millisUntilFinished / 1000).toInt()
            val minutes = seconds / 60
            seconds -= minutes * 60
            setTime(String.format(String.format("%02d", minutes) + ":" + String.format("%02d", seconds)))
        }

        override fun onFinish() {
            binding.editCode.isEnabled = false
            binding.btnCodeSummit.isClickable = false
            setTime("00:00")
        }
    }
    var flagCodeCheak = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val p = Pattern.matches(pwdValidation, "tel")

        binding.containerActivitySignupBinding.setOnClickListener {
            hideKeyboard()
        }

        binding.editSignupNickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkNickName()
            }
        })

        binding.editSignupTel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkTel()
            }
        })

        binding.editSignupPw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPassword()
            }
        })

        binding.editSignupPwCheck.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPasswordEquals()
            }
        })

        binding.btnRequestCode.setOnClickListener {
            if(checkTel()){
                val codeRequest = GetCodeRequest(binding.editSignupTel.text.toString())
                SignUpService(this).tryGetCode(codeRequest)
            }else{
                showCustomToast("전화번호를 확인해주세요")
            }
        }

        binding.btnCodeSummit.setOnClickListener {
            val codeCheckRequest = GetCodeCheckRequest(binding.editSignupTel.text.toString(), binding.editCode.text.toString().toInt())
            SignUpService(this).tryGetCodeCheck(codeCheckRequest)
            showLoadingMessageDialog(this,"인증번호 확인 중입니다")
        }

        binding.btnCodeReset.setOnClickListener {
            val codeRequest = GetCodeRequest(binding.editSignupTel.text.toString())
            SignUpService(this).tryGetCode(codeRequest)
            timer1.cancel()
            timer1.start()
        }

        binding.btnSignUp.setOnClickListener {
            val flag1 = checkTel()
            val flag2 = checkNickName()
            val flag3 = checkPassword()
            val flag4 = checkPasswordEquals()

            if(!flag1 || !flag2 || !flag3 || !flag4){
                showCustomToast("필수입력정보를 확인해주세요")
                return@setOnClickListener
            }
            if(!flagCodeCheak){
                showCustomToast("전화번호 인증이 필요합니다")
                return@setOnClickListener
            }
            val getSignUpRequest = GetSignUpRequest(binding.editSignupNickname.text.toString(),binding.editSignupTel.text.toString(),binding.editSignupPw.text.toString())
            SignUpService(this).tryGetSignUp(getSignUpRequest)
            showLoadingMessageDialog(this,"회원가입 중입니다")
        }

    }

    @SuppressLint("ResourceAsColor")
    fun checkTel():Boolean{
        var tel = binding.editSignupTel.text.toString().trim()
        val p = Pattern.matches(telValidation, tel)
        if (p) {
            binding.editSignupTel.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder)
            binding.tvSignupTelWarning.visibility = View.GONE
            binding.btnRequestCode.isClickable = true
            return true
        } else {
            binding.editSignupTel.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder_red)
            binding.tvSignupTelWarning.visibility = View.VISIBLE
            binding.btnRequestCode.isClickable = false
            return false
        }
    }

    fun checkNickName():Boolean{
        var pwd = binding.editSignupNickname.text.trim().length

        if (pwd > 0) {
            binding.editSignupNickname.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder)
            binding.tvSignupNicknameWarning.visibility = View.GONE
            return true
        } else {
            binding.editSignupNickname.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder_red)
            binding.tvSignupNicknameWarning.visibility = View.VISIBLE
            return false
        }
    }

    fun checkPassword():Boolean{
        var pwd = binding.editSignupPw.text.toString().trim()
        val p = Pattern.matches(pwdValidation, pwd)
        if (p) {
            binding.editSignupPw.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder)
            binding.tvSignupPwWarning.visibility = View.GONE
            return true
        } else {
            binding.editSignupPw.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder_red)
            binding.tvSignupPwWarning.visibility = View.VISIBLE
            return false
        }
    }

    fun checkPasswordEquals():Boolean{
        var pwd = binding.editSignupPw.text.toString().trim()
        var pwdchk = binding.editSignupPwCheck.text.toString().trim()
        if (pwd.equals(pwdchk)) {
            binding.editSignupPwCheck.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder)
            binding.tvSignupPwCheckWarning.visibility = View.GONE
            return true
        } else {
            binding.editSignupPwCheck.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder_red)
            binding.tvSignupPwCheckWarning.visibility = View.VISIBLE
            return false
        }
    }

    fun setTime(time : String){
        binding.tvCountDown.text = time
    }

    override fun onGetSignUpSuccess(response: GetSignUpResponse) {
        dismissLoadingMessageDialog()
        if(response.code != 1000){
            showCustomToast(response.message.toString())
        }else{
            showCustomToast("회원가입 완료")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onGetSignUpFailure(message: String) {
        dismissLoadingMessageDialog()
        showCustomToast(message)
    }

    override fun onPostCodeSuccess(response: BaseResponse) {
        if(response.code != 1002){
            showCustomToast(response.message.toString())
        }else{
            showCustomToast("인증번호를 전송했습니다!")
            binding.editCode.visibility = View.VISIBLE
            binding.linearCodeBtn.visibility = View.VISIBLE
            binding.tvCountDown.visibility = View.VISIBLE
            timer1.start()
        }
    }

    override fun onPostCodeFailure(message: String) {
        showCustomToast(message)
    }

    override fun onPostCodeCheckSuccess(response: BaseResponse) {
        dismissLoadingMessageDialog()
        if(response.code != 1003){
            showCustomToast(response.message.toString())
        }else{
            binding.editSignupTel.isEnabled = false
            binding.editCode.visibility = View.GONE
            binding.linearCodeBtn.visibility = View.GONE
            binding.tvCountDown.visibility = View.GONE
            timer1.cancel()
            flagCodeCheak = true
        }
    }

    override fun onPostCodeCheckFailure(message: String) {
        dismissLoadingMessageDialog()
        showCustomToast(message)
    }
}