package com.winnus.winnus.util

import android.content.Context
import android.graphics.Canvas
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


class VerticalTextView(context: Context?, attrs: AttributeSet?) :
    AppCompatTextView(context!!, attrs) {
    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }

    // 텍스트뷰 가로 입력
    protected override fun onDraw(canvas: Canvas) {

        // Custom View를 생성할때 원하는 폰트 , 색상 , 크기 설정
        val textPaint: TextPaint = getPaint()
        textPaint.color = getCurrentTextColor()
        textPaint.drawableState = getDrawableState()


        // View를 그리위한 객체 Canvas
        canvas.save()
        canvas.translate(0f, height.toFloat())
        canvas.rotate(-90f) // 90도로 회전
        canvas.translate(compoundPaddingLeft.toFloat(), extendedPaddingTop.toFloat())
        layout.draw(canvas)
        canvas.restore() // Canvas 상태를 복원합니다.
    }
}