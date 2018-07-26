package com.example.media.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.media.MyApplication
import com.example.media.R
import com.example.media.VedioManage
import kotlinx.android.synthetic.main.activity_playing.*


class PlayingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing)

    }

    override fun onResume() {
        super.onResume()
        VedioManage.getInstance().addFftListener {
            fft.setData(it)
        }

    }


    override fun onPause() {
        super.onPause()
        VedioManage.getInstance().clearFftListener()
    }

    override fun onDestroy() {
        super.onDestroy()

        val refWatcher = MyApplication.getRefWatcher(applicationContext)
        refWatcher.watch(this)
    }
}
