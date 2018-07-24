package com.example.media.views

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View

class FftSurfaceView : SurfaceView, SurfaceHolder.Callback {
    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        isLIving = false
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        thread = Thread(Runnable() {
            while (true) {
                drawPath()
            }
        })
    }

    constructor(context: Context?) : super(context) {
        initView()
    }


    var withSpec = 0
    var heightSpec = 0
    var fft: ByteArray? = null
    var paint: Paint? = null
    var path: Path? = null
    var isLIving = true
    var thread: Thread? = null


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    private fun initView() {
        holder.addCallback(this)
        setFocusable(true)
        setFocusableInTouchMode(true)

        setKeepScreenOn(true)
        paint = Paint()
        paint?.color = Color.RED
        paint?.setStyle(Paint.Style.FILL);
        paint?.setStrokeWidth(1f)

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        withSpec = View.MeasureSpec.getSize(widthMeasureSpec)
        heightSpec = View.MeasureSpec.getSize(heightMeasureSpec)
    }

    fun setData(fft: ByteArray?) {
        this.fft = fft

    }

    private fun drawPath() {
        if (isLIving) {
            val canvas = holder.lockCanvas()
            canvas?.drawColor(Color.WHITE)
            fft?.forEachIndexed { index, byte ->
                var left = 0 + withSpec * index / 128f
                var down = heightSpec / 2
                var right = left + withSpec / 128f
                var top = down - byte.toInt() * 5
                val rect = Rect(left.toInt(), top.toInt(), right.toInt(), down)
                canvas?.drawRect(rect, paint)
            }
            if (isLIving && canvas != null) {
                holder.unlockCanvasAndPost(canvas)
            }
        }
    }


}