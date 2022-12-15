package com.ejrm.radiocubana.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ejrm.radiocubana.R
import com.ejrm.radiocubana.databinding.EmisoraCardBinding
import com.ejrm.radiocubana.model.EmisoraModel


class EmisoraAdapter(
    private val emisora: List<EmisoraModel>,
    private val onClickListener: (EmisoraModel) -> Unit
) :
    RecyclerView.Adapter<EmisoraAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmisoraAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.emisora_card, parent, false))
    }

    override fun onBindViewHolder(holder: EmisoraAdapter.ViewHolder, position: Int) {
        holder.bind(emisora[position], onClickListener)
    }

    override fun getItemCount(): Int = emisora.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = EmisoraCardBinding.bind(view)

        fun bind(emisor: EmisoraModel, onClickListener: (EmisoraModel) -> Unit) {
            binding.itemTitle.text = emisor.name
            binding.itemDescrip.text = emisor.description
            binding.itemLogo.setImageResource(emisor.imagen)
            itemView.setOnClickListener { onClickListener(emisor) }
        }
    }
}