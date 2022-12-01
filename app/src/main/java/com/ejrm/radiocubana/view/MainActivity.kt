package com.ejrm.radiocubana.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejrm.radiocubana.R
import com.ejrm.radiocubana.databinding.ActivityMainBinding
import com.ejrm.radiocubana.model.EmisoraModel
import com.ejrm.radiocubana.model.EmisoraProvider
import com.ejrm.radiocubana.view.adapters.EmisoraAdapter

class MainActivity : AppCompatActivity() {
    var emisoraList: List<EmisoraModel> = listOf(
    EmisoraModel("https://icecast.teveo.cu/PKWhw37L", "Radio Bahía", "", R.drawable.radio_bahia),
    EmisoraModel("https://icecast.teveo.cu/srJ4vqkv", "Radio Caibarién", "", R.drawable.radio_caibarien),
    EmisoraModel("https://icecast.teveo.cu/fvc4RVRz", "Radio Coco", "", R.drawable.radio_coco))
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniRecyclerView()
    }
    fun iniRecyclerView(){
        val manager = LinearLayoutManager(this)
        binding.recycler.layoutManager = manager
        binding.recycler.adapter = EmisoraAdapter(emisoraList)
    }




}