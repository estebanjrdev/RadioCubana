package com.ejrm.radiocubana.view.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ejrm.radiocubana.R
import com.ejrm.radiocubana.model.EmisoraModel
import com.ejrm.radiocubana.view.EmisoraView

class EmisoraAdapter(emisoraList: List<EmisoraModel>, context: Context) :
    RecyclerView.Adapter<EmisoraAdapter.ViewHolder>() {

    var emisora: List<EmisoraModel> = listOf()
    lateinit var context: Context
    fun EmisoraAdapter(emisora: List<EmisoraModel>, context: Context) {
        this.emisora = emisora
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmisoraAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.emisora_card, parent, false))
    }

    override fun onBindViewHolder(holder: EmisoraAdapter.ViewHolder, position: Int) {
        holder.bind(emisora[position], context)
    }

    override fun getItemCount(): Int = emisora.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.item_title) as TextView
        val description = view.findViewById(R.id.item_descrip) as TextView
        val logo = view.findViewById(R.id.item_logo) as ImageView

        fun bind(emisor: EmisoraModel, context: Context) {
            name.text = emisor.name
            description.text = emisor.description
            logo.setImageResource(emisor.imagen)
            itemView.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, EmisoraView::class.java)
                val bundle = Bundle()
                bundle.putString("name", emisor.name)
                bundle.putString("description", emisor.description)
                bundle.putString("link", emisor.link)
                bundle.putInt("image", emisor.imagen)
                intent.putExtras(bundle)
                context.startActivity(intent)

            })
        }
    }
}