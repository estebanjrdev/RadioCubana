package com.ejrm.radiocubana.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ejrm.radiocubana.databinding.ActivityMainBinding
import com.ejrm.radiocubana.databinding.EmisoraDetailsBinding

class EmisoraView : AppCompatActivity() {
    private lateinit var binding: EmisoraDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmisoraDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var img = intent.extras!!.getInt("imagen")
        var url = intent.extras!!.getCharSequence("link")
        binding.imgWEB.setImageResource(img)
        binding.web.loadUrl(url.toString())
        binding.web.settings.javaScriptEnabled

    }
}