package com.ejrm.radiocubana.services

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.*
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import android.widget.LinearLayout
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ejrm.radiocubana.R
import com.ejrm.radiocubana.util.Constants.CHANNEL_ID
import com.ejrm.radiocubana.util.Constants.RADIO_NOTIFICATION_ID
import com.ejrm.radiocubana.util.MediaPlayerSingleton
import com.ejrm.radiocubana.view.MainActivity


class RadioService : Service() {
    var url: String? = null
    var name: String? = null
    var imagen: Int? = null
    private var myBinder = MyBinder()
    private val TAG: String = "RadioService"
    private lateinit var notificationCustomStyle: Notification
    private lateinit var mediaSession: MediaSessionCompat

    companion object {
        var mediaPlayer: MediaPlayerSingleton? = null

        fun isPlaying() = mediaPlayer!!.isPlaying

        fun controlPlay() {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                } else it.start()
            }
        }

        fun stopRadio() {
            mediaPlayer?.let {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer = null
            }

        }

        fun initReproduction(url: String, context: Context) {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayerSingleton
                mediaPlayer?.initMediaPlayerSingleton(context)
                mediaPlayer?.setDataSource(url)
                mediaPlayer?.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                mediaPlayer?.setScreenOnWhilePlaying(true)
                mediaPlayer?.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
                mediaPlayer?.prepareAsync()
                mediaPlayer?.setOnPreparedListener {
                    mediaPlayer?.start()
                }
            } else if (mediaPlayer != null) {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer = null
                mediaPlayer = MediaPlayerSingleton
                mediaPlayer?.initMediaPlayerSingleton(context)
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
    }

    override fun onBind(p0: Intent?): IBinder {
        mediaSession = MediaSessionCompat(baseContext, TAG)
        return myBinder
    }

    inner class MyBinder : Binder() {
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
        imagen = intent?.getIntExtra("IMAGE",R.mipmap.ic_radio)
        url?.let { initReproduction(it, baseContext) }
        url?.let {
            Log.d("RadioService", "Mensaje $it")
        }
        showNotification()
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
        //val imagen = BitmapFactory.decodeResource(resources,  R.mipmap.ic_radio)
        val notification = NotificationCompat
            .Builder(this, CHANNEL_ID)
            .setContentText(name)
            .setSmallIcon(R.mipmap.ic_radio)
            .setLargeIcon(BitmapFactory.decodeResource(resources, imagen!!))
            .setStyle(NotificationCompat.BigTextStyle().bigText(name))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_pause_24, "Pause", null)
            .addAction(R.drawable.ic_stop_24, "Parar", null)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(RADIO_NOTIFICATION_ID, notification)
    }

    @SuppressLint("RemoteViewLayout")
    fun notification() {
        val notificationLayout = RemoteViews(packageName, R.layout.notification)

        notificationCustomStyle = NotificationCompat.Builder(this, CHANNEL_ID).also {
            it.setSmallIcon(R.mipmap.ic_radio)
            //it.setStyle(NotificationCompat.DecoratedCustomViewStyle())
            it.setCustomContentView(notificationLayout)
            // it.setCustomBigContentView(notificationLayoutExpanded)
        }.build()
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