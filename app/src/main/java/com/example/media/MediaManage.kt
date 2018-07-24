package com.example.media

import java.util.ArrayList

object MediaManage {
    var mediaOp: MediaOp? = null
    var getProgess: (Int) -> Unit = { i: Int -> }
    var currentProgress: Int = 0
    var getSongs: (ArrayList<Song>) -> Unit = {}
    var songChange: (Song) -> Unit = {}
    var currentSong: Song? = null
    var songs: ArrayList<Song>? = null
    var fftListener: (ByteArray?) -> Unit = {}


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

    fun setProgressListener(function: (Int) -> Unit) {
        getProgess = function
    }

    fun setOnSongsLIstener(function: (ArrayList<Song>) -> Unit) {
        getSongs = function
        if (songs != null) {
            function(songs!!)
        }
    }

    fun setSongChangeListener(function: (Song) -> Unit) {
        songChange = function
    }


    fun onFftDataListener(function: (ByteArray?) -> Unit) {
        fftListener = function
    }

}