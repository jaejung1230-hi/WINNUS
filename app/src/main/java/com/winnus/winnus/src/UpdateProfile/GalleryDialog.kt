package com.winnus.winnus.src.UpdateProfile

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import android.widget.Toast
import com.winnus.winnus.R


class GalleryDialog(val context: Context,val UpdateProfileActivityView : UpdateProfileActivityView) {
    private val dialog = Dialog(context)

    fun makeDialog(){
        dialog.setContentView(R.layout.layout_gallery_dialog)
        val btn_goto_gallery = dialog.findViewById<TextView>(R.id.btn_goto_gallery)
        val btn_delete_profile = dialog.findViewById<TextView>(R.id.btn_delete_profile)

        btn_goto_gallery.setOnClickListener {
            UpdateProfileActivityView.goToGallery()
            dialog.dismiss()
        }

        btn_delete_profile.setOnClickListener {
            UpdateProfileActivityView.deleteProfile()
            dialog.dismiss()
        }

        dialog.show()

    }
}