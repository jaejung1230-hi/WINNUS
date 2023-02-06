package com.winnus.winnus.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.winnus.winnus.util.LoadingDialog
import com.winnus.winnus.util.LoadingMessageDialog

// Fragment의 기본을 작성, 뷰 바인딩 활용
abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null
    lateinit var mLoadingDialog: LoadingDialog
    lateinit var mLoadingMessageDialog: LoadingMessageDialog

    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showCustomToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    fun showLoadingMessageDialog(context: Context, text : String) {
        mLoadingMessageDialog = LoadingMessageDialog(context,text)
        mLoadingMessageDialog.show()
    }
    // 띄워 놓은 로딩 다이얼로그를 없앰.
    fun dismissLoadingMessageDialog() {
        if (mLoadingMessageDialog.isShowing) {
            mLoadingMessageDialog.dismiss()
        }
    }

    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }



    fun hideKeyboard(editText: View) {
        val manager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(editText?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
