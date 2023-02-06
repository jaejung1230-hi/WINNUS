package com.winnus.winnus.util

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.winnus.winnus.databinding.DialogLoadingMessageBinding

class LoadingMessageDialog(context: Context, val text: String) : Dialog(context) {
    private lateinit var binding: DialogLoadingMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogLoadingMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvDialogLoadingMessage.text = text
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }
}