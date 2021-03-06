package com.example.media.activitys

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import com.example.media.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    var adapter: RecyclerViewAdapter? = null
    var songs: ArrayList<Song>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MediaManage.setProgressListener { it ->
            progressBar.progress = it

        }

        MediaManage.setSongChangeListener {
            runOnUiThread {
                music_name.text = it.fileName
                progressBar.max = it.duration
            }
        }



        progressBar.progress = MediaManage.currentProgress
        if (MediaManage.currentSong != null) {
            music_name.text = MediaManage.currentSong?.fileName
            progressBar.max = MediaManage.currentSong!!.duration / 60
        }

        adapter = RecyclerViewAdapter(R.layout.music_item, { view, i ->
            val text = view.findViewById<TextView>(R.id.music_text)
            text.text = songs!![i].fileName
            view.setOnClickListener {
                MediaManage.playing(i)
            }
        })
        val layoutManager = object : LinearLayoutManager(this) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        music_list.layoutManager = layoutManager
        music_list.adapter = adapter

        requestAllPower()

        control.setOnClickListener {
            startActivity<PlayingActivity>()
        }

    }

    override fun onResume() {
        super.onResume()

    }

    fun requestAllPower() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO), 1)
            }
        } else {
            getSongs()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode === 1) {
            for (i in 0 until permissions.size) {
                if (grantResults[i] === PERMISSION_GRANTED) {
                    getSongs()
                } else {

                }
            }
        }
    }

    private fun getSongs() {
        MediaManage.setOnSongsLIstener {
            songs = it
            adapter?.count = songs!!.size
            adapter?.notifyDataSetChanged()
        }

        startService(Intent(this, DeamonPlay::class.java))
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
