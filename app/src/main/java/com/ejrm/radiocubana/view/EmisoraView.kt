package com.ejrm.radiocubana.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ejrm.radiocubana.databinding.ActivityMainBinding

class EmisoraView : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}