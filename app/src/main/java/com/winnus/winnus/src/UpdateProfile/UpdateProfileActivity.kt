package com.winnus.winnus.src.UpdateProfile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.winnus.winnus.R
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.ApplicationClass.Companion.LOG_IN_USER
import com.winnus.winnus.config.BaseActivity
import com.winnus.winnus.config.BaseResponse
import com.winnus.winnus.databinding.ActivityUpdateProfileBinding
import com.winnus.winnus.src.UpdateProfile.model.PatchUpdateProfileRequest
import com.winnus.winnus.src.gallery.GalleryActivity
import com.winnus.winnus.src.withDraw.WithDrawActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class UpdateProfileActivity : com.winnus.winnus.config.BaseActivity<ActivityUpdateProfileBinding>(ActivityUpdateProfileBinding::inflate), UpdateProfileActivityView {
    var pwdValidation = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,15}\$"
    lateinit var changeProfile : String
    lateinit var beforeProfile : String
    private lateinit var storage : FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storage = FirebaseStorage.getInstance()

        binding.constraintUpdateProfile.setOnClickListener {
            hideKeyboard()
        }

        binding.tvGoWithdraw.setOnClickListener {
            val intent = Intent(this,WithDrawActivity::class.java)
            startActivity(intent)
        }

        binding.btnUpdateProflie.setOnClickListener {
            val flag1 = checkNickName()
            val flag2 = checkOldPassword()
            val flag3 = checkNewPassword()
            if(flag1 && flag2 && flag3){
                showLoadingMessageDialog(this,"회원 정보 변경 중입니다.")

                if(changeProfile == beforeProfile || changeProfile == "https://test.winnus.club/winnusImgs/userProfile.jpg"){
                    val patchUpdateProfileRequest = PatchUpdateProfileRequest(changeProfile,binding.editNickName.text.toString(),binding.editOldPassword.text.toString(),binding.editNewPassword.text.toString())
                    UpdateProfileActivityService(this).tryPatchUpdateProfile(patchUpdateProfileRequest)
                }else{
                    val now = System.currentTimeMillis()
                    val date = Date(now)
                    val sdf = SimpleDateFormat("yyyy_MM_DD_hh_mm_ss")
                    val getTime = sdf.format(date)
                    val filename = "${getTime}_${com.winnus.winnus.config.ApplicationClass.sSharedPreferences.getInt(LOG_IN_USER,-1)}"

                    val storageRef : StorageReference = storage.reference
                    val riversRef : StorageReference = storageRef.child("$filename.jpg")

                    val uploadTask : UploadTask? = riversRef.putFile(Uri.fromFile(File(changeProfile)))
                    val photoUri = "https://firebasestorage.googleapis.com/v0/b/ssac-test-7751b.appspot.com/o/$filename.jpg?alt=media"

                    uploadTask?.addOnSuccessListener {
                        val patchUpdateProfileRequest = PatchUpdateProfileRequest(photoUri,binding.editNickName.text.toString(),binding.editOldPassword.text.toString(),binding.editNewPassword.text.toString())
                        UpdateProfileActivityService(this).tryPatchUpdateProfile(patchUpdateProfileRequest)
                    }
                    uploadTask?.addOnFailureListener{
                        dismissLoadingMessageDialog()
                    }
                }

            }
        }

        //텍스트뷰 밑줄귿기
        binding.tvGoWithdraw.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.editNickName.setText(intent.getStringExtra("name"))
        Glide.with(this)
            .load(intent.getStringExtra("url"))
            .error(R.color.gray)
            .into(binding.imgProfile)
        changeProfile = intent.getStringExtra("url").toString()
        beforeProfile = intent.getStringExtra("url").toString()
        binding.editNickName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkNickName()
            }
        })

        binding.editOldPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkOldPassword()
            }
        })

        binding.editNewPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkNewPassword()
            }
        })

        binding.imgProfile.setOnClickListener {
            GalleryDialog(this,this).makeDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1002) {
            if (resultCode == RESULT_OK) {
                val currentImageUri = data?.getStringExtra("currentImageUri")
                if(currentImageUri != null){
                    changeProfile = currentImageUri!!
                    Glide.with(this)
                        .load(currentImageUri)
                        .error(R.mipmap.ic_launcher)
                        .apply(RequestOptions().centerCrop())
                        .into(binding.imgProfile)
                    BitmapFactory.decodeFile(currentImageUri)
                }
            }
        }
    }

    fun checkNickName():Boolean{
        var pwd = binding.editNickName.text.trim().length

        if (pwd > 0) {
            binding.editNickName.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder)
            binding.tvNicknameWarning.visibility = View.GONE
            return true
        } else {
            binding.editNickName.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder_red)
            binding.tvNicknameWarning.visibility = View.VISIBLE
            return false
        }
    }

    fun checkOldPassword():Boolean{
        var pwd = binding.editOldPassword.text.toString().trim()
        if(pwd.isNotEmpty()){
            val p = Pattern.matches(pwdValidation, pwd)
            if (p) {
                binding.editOldPassword.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder)
                binding.tvOldPasswordWarning.visibility = View.GONE
                binding.tvOldPasswordWarning.text = "비밀번호는 8자리 이상, 대,소문자와 숫자를 포함해야합니다!"
                return true
            } else {
                binding.editOldPassword.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder_red)
                binding.tvOldPasswordWarning.visibility = View.VISIBLE
                binding.tvOldPasswordWarning.text = "비밀번호는 8자리 이상, 대,소문자와 숫자를 포함해야합니다!"
                return false
            }
        }else{
            binding.editOldPassword.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder)
            binding.tvOldPasswordWarning.visibility = View.GONE
            binding.tvOldPasswordWarning.text = "비밀번호는 8자리 이상, 대,소문자와 숫자를 포함해야합니다!"
            return true
        }
    }

    fun checkNewPassword():Boolean{
        var pwd = binding.editNewPassword.text.toString().trim()
        val p = Pattern.matches(pwdValidation, pwd)
        if(pwd.isNotEmpty()){
            if (p) {
                binding.editNewPassword.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder)
                binding.tvNewPasswordWarning.visibility = View.GONE
                return true
            } else {
                binding.editNewPassword.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder_red)
                binding.tvNewPasswordWarning.visibility = View.VISIBLE
                return false
            }
        }else{
            binding.editNewPassword.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder)
            binding.tvNewPasswordWarning.visibility = View.GONE
            return true
        }
    }

    override fun onPatchProfileSuccess(response: BaseResponse) {
        dismissLoadingMessageDialog()
        if(response.code == 1007){
            finish()
        }
        else if(response.code == 3054){
            binding.editOldPassword.background = ContextCompat.getDrawable(this, R.drawable.edit_bottom_boarder_red)
            binding.tvOldPasswordWarning.visibility = View.VISIBLE
            binding.tvOldPasswordWarning.text = response.message
        }
    }

    override fun onPatchProfileFailure(message: String) {
        dismissLoadingMessageDialog()
        showCustomToast(message)
    }

    override fun deleteProfile() {

        changeProfile = "https://test.winnus.club/winnusImgs/userProfile.jpg"
        Glide.with(this)
            .load(changeProfile)
            .error(R.mipmap.ic_launcher)
            .apply(RequestOptions().centerCrop())
            .into(binding.imgProfile)
    }

    override fun goToGallery() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),101)
        }
        else{
            val intent = Intent(this, GalleryActivity::class.java)
            startActivityForResult(intent,1002)
        }
    }
}