package com.ejrm.radiocubana.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ejrm.radiocubana.R
import com.ejrm.radiocubana.model.EmisoraModel

class EmisoraAdapter(val emisora:List<EmisoraModel>) : RecyclerView.Adapter<EmisoraAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmisoraAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.emisora_card, parent, false))
    }

    override fun onBindViewHolder(holder: EmisoraAdapter.ViewHolder, position: Int) {
        holder.bind(emisora[position])
    }

    override fun getItemCount(): Int = emisora.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.item_title) as TextView
        val description = view.findViewById(R.id.item_descrip) as TextView
        val logo = view.findViewById(R.id.item_logo) as ImageView
        fun bind(emisor:EmisoraModel){
            name.text = emisor.name
            description.text = emisor.description
            logo.setImageResource(emisor.imagen)
        }
    }
}