package com.sayamphoo.kruskals.media

import android.media.MediaPlayer

class MediaKruskal {
    private var mediaPlayer: MediaPlayer? = null
    fun setMediaPlay() {
     // mediaPlayer = MediaPlayer.create(R.raw.)
    }

    fun playMedia() {
        if (mediaPlayer != null){
            mediaPlayer?.start()
        }
    }
     fun stopMedia() {
         if (mediaPlayer!!.isPlaying){
             mediaPlayer?.start()
         }
     }

}