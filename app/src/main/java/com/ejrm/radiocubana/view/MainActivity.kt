package com.ejrm.radiocubana.view

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejrm.radiocubana.R
import com.ejrm.radiocubana.databinding.ActivityMainBinding
import com.ejrm.radiocubana.model.EmisoraModel
import com.ejrm.radiocubana.model.EmisoraProvider
import com.ejrm.radiocubana.view.adapters.EmisoraAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
        binding.layoutReproduction.visibility = LinearLayout.VISIBLE
        binding.imagelogo.setImageResource(emisora.imagen)
        binding.tilte.text = emisora.name
        binding.imagePlay.setImageResource(R.drawable.ic_pause_24)
        val url = emisora.link
        val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(url)
            prepare()
            start()
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