package com.winnus.winnus.src.allReview

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.winnus.winnus.R


class ReportDialog(val context: Context, val id: Int, val allReviewActivityView: AllReviewActivityView) {
    private val dialog = Dialog(context)
    var reasonList = mutableListOf<String>("신고 이유를 선택해주세요.","도용", "욕설 및 비방",  "기타")

    fun makeDialog() {
        dialog.setContentView(R.layout.layout_report_dialog)
        val btn_report_yes = dialog.findViewById<TextView>(R.id.btn_report_yes)
        val btn_report_no = dialog.findViewById<TextView>(R.id.btn_report_no)
        val spinner_why_report = dialog.findViewById<Spinner>(R.id.spinner_why_report)


        val spinnerAdapter = object : ArrayAdapter<String>(context, R.layout.spinner_item, reasonList) { override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view: TextView =
                        super.getDropDownView(position, convertView, parent) as TextView

                    if (position == 0) {
                        view.setTextColor(Color.GRAY)
                    } else {
                    }
                    return view
                }
            }

        spinner_why_report.adapter = spinnerAdapter

        spinner_why_report.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if (value == reasonList[0]) {
                    (view as TextView).setTextColor(Color.GRAY)
                }
            }
        }

        btn_report_yes.setOnClickListener {
            if (spinner_why_report.selectedItemPosition == 0) {
                Toast.makeText(context, "신고 사유를 알려주세요", Toast.LENGTH_SHORT).show()
            } else {
                allReviewActivityView.onPostReport(id, spinner_why_report.selectedItemPosition)
                dialog.dismiss()
            }

            btn_report_no.setOnClickListener {

                dialog.dismiss()
            }
        }

        dialog.show()
    }
}