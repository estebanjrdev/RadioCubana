package com.ejrm.radiocubana.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejrm.radiocubana.databinding.ActivityMainBinding
import com.ejrm.radiocubana.model.EmisoraModel
import com.ejrm.radiocubana.model.EmisoraProvider
import com.ejrm.radiocubana.view.adapters.EmisoraAdapter

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
        val intent = Intent(this, EmisoraView::class.java)
        val bundle = Bundle()
        bundle.putString("name", emisora.name)
        bundle.putString("description", emisora.description)
        bundle.putString("link", emisora.link)
        bundle.putInt("image", emisora.imagen)
        intent.putExtras(bundle)
        startActivity(intent)
    }


}