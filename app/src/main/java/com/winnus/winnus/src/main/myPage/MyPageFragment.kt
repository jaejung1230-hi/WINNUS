package com.winnus.winnus.src.main.myPage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.winnus.winnus.R
import com.winnus.winnus.config.ApplicationClass
import com.winnus.winnus.config.BaseFragment
import com.winnus.winnus.databinding.FragmentMyPageBinding
import com.winnus.winnus.src.UpdateProfile.UpdateProfileActivity
import com.winnus.winnus.src.bookMarkWine.SubscribesWinesActivity
import com.winnus.winnus.src.login.LoginActivity
import com.winnus.winnus.src.main.myPage.model.ProfileResponse
import com.winnus.winnus.src.myReview.MyReviewActivity
import com.winnus.winnus.src.simple.IntroduceActivity
import com.winnus.winnus.src.simple.NoticeActivity
import com.winnus.winnus.src.simple.VocActivity


class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page), MyPageFragmentView {
    lateinit var url : String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            val editor = com.winnus.winnus.config.ApplicationClass.sSharedPreferences.edit()
            editor.putString(com.winnus.winnus.config.ApplicationClass.X_ACCESS_TOKEN, null)
            editor.commit()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            context?.startActivity(intent)
            ActivityCompat.finishAffinity(requireActivity())
        }

        binding.btnIntroduce.setOnClickListener {
            val intent = Intent(requireContext(),IntroduceActivity::class.java)
            startActivity(intent)
        }

        binding.btnVoc.setOnClickListener {
            val intent = Intent(requireContext(), VocActivity::class.java)
            startActivity(intent)
        }

        binding.btnNotice.setOnClickListener {
            val intent = Intent(requireContext(),NoticeActivity::class.java)
            startActivity(intent)
        }

        binding.btnIntroduce.setOnClickListener {
            val intent = Intent(requireContext(),IntroduceActivity::class.java)
            startActivity(intent)
        }

        binding.imgProfile.setOnClickListener {
            val intent = Intent(requireContext(),UpdateProfileActivity::class.java)
            if(this::url.isInitialized){
                intent.putExtra("url", url)
            }
            intent.putExtra("name", binding.tvMyName.text)
            context?.startActivity(intent)
        }
        binding.btnUpdateProflie.setOnClickListener {
            val intent = Intent(requireContext(),UpdateProfileActivity::class.java)
            if(this::url.isInitialized){
                intent.putExtra("url", url)
            }
            intent.putExtra("name", binding.tvMyName.text)
            context?.startActivity(intent)
        }

        binding.btnMyReview.setOnClickListener {
            val intent = Intent(context,MyReviewActivity::class.java)
            context?.startActivity(intent)
        }

        binding.btnMySubscribes.setOnClickListener {
            val intent = Intent(context,SubscribesWinesActivity::class.java)
            context?.startActivity(intent)
        }

        /*
        else{
            val now = System.currentTimeMillis()
            val date = Date(now)
            val sdf = SimpleDateFormat("yyyy_MM_DD_hh_mm_ss")
            val getTime = sdf.format(date)
            val filename = "${getTime}_${ApplicationClass.sSharedPreferences.getInt(LOG_IN_USER,-1)}.jpg"

            val storageRef : StorageReference = storage.reference
            val riversRef : StorageReference = storageRef.child("$filename.jpg")
            val uploadTask : UploadTask? = file?.let { it1 -> riversRef.putFile(it1) }
            val photoUri = "https://firebasestorage.googleapis.com/v0/b/ssac-test-7751b.appspot.com/o/$filename.jpg?alt=media"
            Log.d("photoUri",photoUri)
            uploadTask?.addOnSuccessListener {
                val writeReviewRequest = WriteReviewRequest(selectedItem!!.itemId!!, photoUri, binding.editComment.text.toString(),
                    binding.ratingbarPoint.rating.toInt(),  binding.ratingbarPoint.rating.toInt(),  binding.ratingbarPoint.rating.toInt(),  binding.ratingbarPoint.rating.toInt())
                WriteReviewService(this).tryPostReview(ApplicationClass.sSharedPreferences.getInt(LOG_IN_USER,-1), writeReviewRequest)
            }

        }
         */
    }

    override fun onResume() {
        super.onResume()
        showLoadingMessageDialog(requireContext(),"사용자 정보를 불러오는 중입니다.")
        MyPageFragmentService(this).tryGetProfile()
    }

    @SuppressLint("SetTextI18n")
    override fun onGetProfileSuccess(response: ProfileResponse) {
        dismissLoadingMessageDialog()
        if(response.code == 1000){
            binding.tvMyName.text = response.result[0].nickname
            binding.tvMyTel.text = response.result[0].phoneNum.substring(0,3) + " " + response.result[0].phoneNum.substring(3,response.result[0].phoneNum.length-4) + " "  + response.result[0].phoneNum.substring(response.result[0].phoneNum.length-4,response.result[0].phoneNum.length)
            url = response.result[0].profileImg
            Glide.with(this)
                .load(response.result[0].profileImg)
                .error(R.color.gray)
                .into(binding.imgProfile)
        }
    }

    override fun onGetProfileFailure(message: String) {
        dismissLoadingMessageDialog()
        showCustomToast(message)
    }
}