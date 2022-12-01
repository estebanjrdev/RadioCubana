package com.ejrm.radiocubana.model

import com.ejrm.radiocubana.R

class EmisoraProvider {

    companion object{
      private val emisoraList = listOf<EmisoraModel>(
            EmisoraModel("https://icecast.teveo.cu/PKWhw37L","Radio Bahía","", R.drawable.radio_bahia),
            EmisoraModel("https://icecast.teveo.cu/srJ4vqkv","Radio Caibarién","", R.drawable.radio_caibarien),
            EmisoraModel("https://icecast.teveo.cu/fvc4RVRz","Radio Coco","", R.drawable.radio_coco)
        )
    }
}