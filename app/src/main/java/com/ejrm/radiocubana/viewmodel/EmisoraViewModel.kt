package com.ejrm.radiocubana.viewmodel

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ejrm.radiocubana.model.EmisoraModel
import com.ejrm.radiocubana.view.EmisoraView

class EmisoraViewModel: ViewModel() {
    val emisoralist = MutableLiveData<List<EmisoraModel>>()
    fun openEmisora(view: View){
      //  val intent = Intent(this, EmisoraView::class.java)
      //  startActivity(intent)
    }
}