package com.winnus.winnus.src.withDraw

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.winnus.winnus.R
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.databinding.ActivityWithDrawBinding
import com.winnus.winnus.src.login.LoginActivity
import com.winnus.winnus.src.withDraw.model.PatchWithDrawRequest
import java.util.regex.Pattern

class WithDrawActivity : com.winnus.winnus.config.BaseActivity<ActivityWithDrawBinding>(ActivityWithDrawBinding::inflate),WithDrawActivityView  {
    var reasonList = mutableListOf<String>("탈퇴 이유를 선택해주세요.","자주 쓰지 않는 앱이에요","원하는 정보가 없어요", "검색 시스템이 복잡해요", "앱에 오류가 있어요", "앱을 어떻게 쓰는지 모르겠어요", "기타")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val spinnerAdapter= object : ArrayAdapter<String>(this, R.layout.spinner_item, reasonList) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView

                if(position == 0) { view.setTextColor(Color.GRAY) } else { }
                return view
            }
        }

        binding.spinnerWhyWithDraw.adapter = spinnerAdapter

        binding.spinnerWhyWithDraw.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val value = parent!!.getItemAtPosition(position).toString()
                if(value == reasonList[0]){
                    (view as TextView).setTextColor(Color.GRAY)
                }
            }
        }

        binding.editPwForWithDraw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPassword()
            }
        })

        binding.btnUpdateProflie.setOnClickListener {
            if(!checkPassword()){

            }else if(binding.spinnerWhyWithDraw.selectedItemPosition == 0){
                showCustomToast("탈퇴 사유를 알려주세요")
            }else{
                showLoadingMessageDialog(this,"회원 삭제중입니다...")
                WithDrawActivityService(this).tryPatchWithDraw(PatchWithDrawRequest(binding.editPwForWithDraw.text.toString(),binding.spinnerWhyWithDraw.selectedItemPosition))

            }

        }

    }

    override fun onWithDrawSuccess(response: BaseResponse) {
        dismissLoadingMessageDialog()
        if(response.code == 1008){
            val editor = com.winnus.winnus.config.ApplicationClass.sSharedPreferences.edit()
            editor.putString(com.winnus.winnus.config.ApplicationClass.X_ACCESS_TOKEN, null)
            editor.commit()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }else if(response.code == 3043){
            binding.editPwForWithDraw.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder_red)
            binding.tvPasswordWarning.visibility = View.VISIBLE
            binding.tvPasswordWarning.text = response.message
        }else{
            showCustomToast(response.message.toString())
        }
    }

    override fun onWithDrawFailure(message: String) {
        dismissLoadingMessageDialog()
        showCustomToast(message)
    }

    var pwdValidation = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,15}\$"
    fun checkPassword():Boolean{
        var pwd = binding.editPwForWithDraw.text.toString().trim()

            val p = Pattern.matches(pwdValidation, pwd)
            if (p) {
                binding.editPwForWithDraw.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder)
                binding.tvPasswordWarning.visibility = View.GONE

                return true
            } else {
                binding.editPwForWithDraw.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder_red)
                binding.tvPasswordWarning.visibility = View.VISIBLE
                binding.tvPasswordWarning.text = "비밀번호는 8자리 이상, 대,소문자와 숫자를 포함해야합니다!"
                return false
            }

    }
}