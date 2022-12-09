package com.ejrm.radiocubana.view

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
        binding.imgWEB.setImageResource(intent.extras!!.getInt("imagen"))
        var url = intent.extras!!.getCharSequence("link")
        binding.web.loadUrl(url.toString())
        binding.web.settings.javaScriptEnabled

    }
}