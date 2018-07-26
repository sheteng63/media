package com.example.media.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.example.media.R

class FftSurfaceView : View {

    constructor(context: Context?) : super(context) {
        initView()
    }


    private var withSpec = 0
    private var heightSpec = 0
    private var fft: ByteArray? = null
    private var paint: Paint? = null
    private var path: Path? = null
    private var list = ArrayList<Bitmap>()
    private var bitmap: Bitmap? = null


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    private fun initView() {
        paint = Paint()
        paint?.color = Color.RED
        bitmap = Bitmap.createBitmap(50,50,Bitmap.Config.ARGB_8888)

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        withSpec = View.MeasureSpec.getSize(widthMeasureSpec)
        heightSpec = View.MeasureSpec.getSize(heightMeasureSpec)
    }

    fun setData(fft: ByteArray?) {
        println(fft)
        this.fft = fft

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0..16) {
            if (i % 1 == 0) {
                val left = 0 + withSpec * i / 128 * 8
                val down = heightSpec / 2
                val right = left + withSpec / 128 * 8
                val top = down - fft!![i]
                val rectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), down.toFloat())
                val matrix = Matrix()
                matrix.mapRect(rectF)
                canvas?.drawBitmap(bitmap,matrix,paint)
            }
        }
    }


}