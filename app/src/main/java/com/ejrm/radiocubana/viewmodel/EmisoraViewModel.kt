package com.ejrm.radiocubana.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ejrm.radiocubana.model.EmisoraModel

class EmisoraViewModel: ViewModel() {
    val emisoralist = MutableLiveData<List<EmisoraModel>>()
}