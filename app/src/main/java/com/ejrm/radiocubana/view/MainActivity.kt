package com.ejrm.radiocubana.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejrm.radiocubana.databinding.ActivityMainBinding
import com.ejrm.radiocubana.model.EmisoraProvider.Companion.emisoraList
import com.ejrm.radiocubana.view.adapters.EmisoraAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniRecyclerView(baseContext)
    }

    fun iniRecyclerView(context: Context) {
        val manager = LinearLayoutManager(this)
        binding.recycler.layoutManager = manager
        binding.recycler.adapter = EmisoraAdapter(emisoraList, context)
    }


}