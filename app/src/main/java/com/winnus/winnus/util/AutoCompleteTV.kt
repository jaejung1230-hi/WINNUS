package com.winnus.winnus.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

import androidx.appcompat.widget.AppCompatAutoCompleteTextView


class AutoCompleteTV : AppCompatAutoCompleteTextView {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr
    ) {
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            performClick()
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}