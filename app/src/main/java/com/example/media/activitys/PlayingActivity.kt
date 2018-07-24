package com.example.media.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.media.MediaManage
import com.example.media.R
import kotlinx.android.synthetic.main.activity_playing.*

class PlayingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing)

    }

    override fun onResume() {
        super.onResume()
        MediaManage.onFftDataListener {
            fft.setData(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
