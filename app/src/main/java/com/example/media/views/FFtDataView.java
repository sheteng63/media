package com.example.media.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import org.jetbrains.annotations.Nullable;

public class FFtDataView extends SurfaceView implements SurfaceHolder.Callback {


    private SurfaceHolder holder;
    private Thread thread;
    private Runnable runnable;

    public FFtDataView(Context context) {
        super(context);
    }

    public FFtDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        System.out.println("FFtDataView");
        initView();
    }

    Paint paint;
    int withSpec;
    int heightSpec;
    byte[] bytes;
    boolean isliving = true;

    private void initView() {
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1f);


    }

    private void initThread() {
        isliving = true;
        runnable = new Runnable() {
            @Override
            public void run() {
                while (isliving) {
                    drawPath();
                }
            }
        };
        thread = new Thread(runnable);
        thread.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        withSpec = View.MeasureSpec.getSize(widthMeasureSpec);
        heightSpec = View.MeasureSpec.getSize(heightMeasureSpec);
    }


    public void setData(byte[] bytes) {
        this.bytes = bytes;
        if (thread != null) {
            synchronized (this) {
                this.notify();

            }
        }

    }


    private void drawPath() {

        try {
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            for (int i = 0; i < bytes.length; i++) {
                if (i % 1 == 0) {
                    int left = 0 + withSpec * i / 128;
                    int down = heightSpec / 2;
                    int right = left + withSpec / 128;
                    int top = down - Math.abs(bytes[i]) * 5;
                    RectF rectF = new RectF(left, top, right, down);
                    canvas.drawRect(rectF, paint);
                }
            }
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }


        } catch (Exception e) {

        }
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        System.out.println("surfaceCreated");
        initThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        System.out.println("surfaceDestroyed");
        isliving = false;
        synchronized (this) {
            try {
                this.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
