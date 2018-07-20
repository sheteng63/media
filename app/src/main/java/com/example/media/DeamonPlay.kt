package com.example.media

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.MediaStore
import kotlinx.coroutines.experimental.*


class DeamonPlay : Service(), MediaOp {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    var status = Status.STOP
    var playingId = 0
    var mediaPlayer: MediaPlayer? = null

    var songs: ArrayList<Song>? = null

    var lau: Job? = null

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer?.setOnCompletionListener {
            if (playingId == songs?.size!! - 1) {
                playingId = -1
            }
            start(playingId + 1)
        }

        MediaManage.setService(this)
        songs =  AudioUtils.getAllSongs(this)
        MediaManage.getSongs(songs!!)
        MediaManage.songs = songs
    }


    override fun playing(i: Int) {
        if (status == Status.STOP) {
            start(i)
        } else if (status == Status.PALYING) {
            if (playingId == i) {
                mediaPlayer?.pause()
                status = Status.PAUSE
            } else {
                start(i)
            }
        } else if (status == Status.PAUSE) {
            mediaPlayer?.start()
            status = Status.PALYING
        }
    }

    fun start(i: Int) {

        mediaPlayer?.reset()
        mediaPlayer?.setDataSource(songs!![i].fileUrl)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
        status = Status.PALYING
        lau = launch(CommonPool) {
            while (status == Status.PALYING) {
                delay(1000)
                MediaManage.getProgess(songs!![playingId],mediaPlayer?.currentPosition!!)
            }
        }
        playingId = i
    }

    override fun stop() {
        mediaPlayer?.stop()
        status = Status.STOP
    }

    override fun pause() {
        if (status == Status.PAUSE) {
            mediaPlayer?.start()
            status = Status.PALYING
        } else {
            mediaPlayer?.pause()
            status = Status.PAUSE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

}
