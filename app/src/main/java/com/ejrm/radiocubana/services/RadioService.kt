package com.ejrm.radiocubana.services

import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.*
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ejrm.radiocubana.R
import com.ejrm.radiocubana.util.Constants.CHANNEL_ID
import com.ejrm.radiocubana.util.Constants.RADIO_NOTIFICATION_ID
import com.ejrm.radiocubana.util.MediaPlayerSingleton
import com.ejrm.radiocubana.view.MainActivity


class RadioService : Service() {
    var url:String? = null
    var name:String? = null
    var imagen:Int? = R.mipmap.ic_radio
    private  var myBinder = MyBinder()
    private val TAG: String = "RadioService"
    private var mediaPlayer: MediaPlayerSingleton? = null
    private lateinit var mediaSession : MediaSessionCompat
    override fun onBind(p0: Intent?): IBinder {
        mediaSession = MediaSessionCompat(baseContext,TAG)
        return myBinder
    }

    inner class MyBinder: Binder(){
        fun currentService(): RadioService {
            return this@RadioService
        }
    }
    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        url = intent?.getStringExtra("URL")
        name = intent?.getStringExtra("NAME")
       // imagen = intent?.getIntExtra("IMAGE")
        url?.let { initReproduction(it) }
        url?.let {
Log.d("RadioService","Mensaje $it")
        }
showNotification()
        return START_REDELIVER_INTENT
    }
    private fun initReproduction(url: String) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayerSingleton
            mediaPlayer?.initMediaPlayerSingleton(this)
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            mediaPlayer?.setScreenOnWhilePlaying(true)
            mediaPlayer?.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            mediaPlayer?.prepareAsync()
            mediaPlayer?.setOnPreparedListener {
                mediaPlayer?.start()
            }
        } else if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer = null
            mediaPlayer = MediaPlayerSingleton
            mediaPlayer?.initMediaPlayerSingleton(this)
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            mediaPlayer?.prepareAsync()
            mediaPlayer?.setOnPreparedListener {
                mediaPlayer?.start()
            }

        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
        val notification = androidx.core.app.NotificationCompat
            .Builder(this, CHANNEL_ID)
            .setContentText(name)
            .setSmallIcon(R.mipmap.ic_radio)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.mipmap.ic_radio))
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.drawable.ic_pause_24,"Pause",null)
            .addAction(R.drawable.ic_stop_24,"Parar",null)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(RADIO_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "My Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}