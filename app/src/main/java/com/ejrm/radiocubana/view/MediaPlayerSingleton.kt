package com.ejrm.radiocubana.view

import android.content.Context
import android.media.MediaPlayer

object MediaPlayerSingleton: MediaPlayer() {
    lateinit var context: Context
    fun initMediaPlayerSingleton(context: Context){
        this.context = context.applicationContext
    }
}