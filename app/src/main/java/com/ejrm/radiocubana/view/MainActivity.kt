package com.ejrm.radiocubana.view

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejrm.radiocubana.R
import com.ejrm.radiocubana.databinding.ActivityMainBinding
import com.ejrm.radiocubana.model.EmisoraModel
import com.ejrm.radiocubana.model.EmisoraProvider
import com.ejrm.radiocubana.view.adapters.EmisoraAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniRecyclerView()
    }

    fun iniRecyclerView() {
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = EmisoraAdapter(EmisoraProvider.getEmisoras()) { emisora ->
            itemSelected(
                emisora
            )
        }
    }

    fun itemSelected(emisora: EmisoraModel) {
        val url = emisora.link
        mediaPlayer = MediaPlayer()
        mediaPlayer.reset()
        mediaPlayer.setDataSource(url)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener{
            binding.layoutReproduction.visibility = LinearLayout.VISIBLE
            binding.imagelogo.setImageResource(emisora.imagen)
            mediaPlayer.start()

            binding.btnPause.setOnClickListener(View.OnClickListener {
                if (mediaPlayer.isPlaying){
                    mediaPlayer.pause()
                }else{
                    Toast.makeText(this,"No hay reproduccion",Toast.LENGTH_SHORT).show()
                }
            })
            binding.btnPlay.setOnClickListener(View.OnClickListener {
                if (!mediaPlayer.isPlaying){
                    mediaPlayer.start()
                }else{
                    Toast.makeText(this,"hay reproduccion",Toast.LENGTH_SHORT).show()
                }
            })
            binding.btnStop.setOnClickListener(View.OnClickListener {
                if (mediaPlayer.isPlaying){
                    mediaPlayer.stop()
                }else{
                    Toast.makeText(this,"no hay reproduccion",Toast.LENGTH_SHORT).show()
                }
            })
        }

        /* val intent = Intent(this, EmisoraView::class.java)
         val bundle = Bundle()
         bundle.putString("name", emisora.name)
         bundle.putString("description", emisora.description)
         bundle.putString("link", emisora.link)
         bundle.putInt("image", emisora.imagen)
         intent.putExtras(bundle)
         startActivity(intent)*/
    }

}