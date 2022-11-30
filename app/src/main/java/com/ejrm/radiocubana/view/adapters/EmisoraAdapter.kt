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

class EmisoraAdapter : RecyclerView.Adapter<EmisoraAdapter.ViewHolder>() {
    var emisora: MutableList<EmisoraModel>  = ArrayList()
    lateinit var context: Context

    fun RecyclerAdapter(emisora : MutableList<EmisoraModel>, context: Context){
        this.emisora = emisora
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmisoraAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.emisora_card, parent, false))
    }

    override fun onBindViewHolder(holder: EmisoraAdapter.ViewHolder, position: Int) {
        val item = emisora.get(position)
        holder.bind(item, context)
    }

    override fun getItemCount(): Int {
        return emisora.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.item_title) as TextView
        val description = view.findViewById(R.id.item_descrip) as TextView
        val logo = view.findViewById(R.id.item_logo) as ImageView
        fun bind(superhero:EmisoraModel, context: Context){

        }
    }
}