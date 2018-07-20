package com.example.media

import java.util.ArrayList

object MediaManage {
    var mediaOp: MediaOp? = null
    var getProgess: (Song, Int) -> Unit = { song: Song, i: Int -> }
    var getSongs: (ArrayList<Song>) -> Unit = {}
    var songs: ArrayList<Song>? = null
    fun setService(mediaOp: MediaOp) {
        this.mediaOp = mediaOp
    }


    fun playing(i: Int) {
        mediaOp?.playing(i)
    }

    fun pause() {
        mediaOp?.pause()
    }

    fun stop() {
        mediaOp?.stop()
    }

    fun setProgressListener(function: (Song, Int) -> Unit) {
        getProgess = function
    }

    fun setOnSongsLIstener(function: (ArrayList<Song>) -> Unit) {
        getSongs = function
        if (songs != null) {
            function(songs!!)
        }
    }

}