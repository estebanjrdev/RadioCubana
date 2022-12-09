package com.ejrm.radiocubana.model

import com.ejrm.radiocubana.R

class EmisoraProvider {

    companion object{
          fun getEmisoras(): List<EmisoraModel> = emisoraList
     private  val emisoraList = listOf<EmisoraModel>(
           EmisoraModel("https://icecast.teveo.cu/kHKL7tWd","Radio Rebelde(AM)","Emisora de la Revolución Cubana", R.drawable.radio_rebelde),
           EmisoraModel("https://icecast.teveo.cu/zrXXWK9F","Radio Rebelde(FM)","Emisora de la Revolución Cubana", R.drawable.radio_rebeldefm),
           EmisoraModel("https://icecast.teveo.cu/XjfW7qWN","Radio Progreso","La Onda de la Alegría", R.drawable.radio_progreso),
           EmisoraModel("https://icecast.teveo.cu/3MCwWg3V","Radio Taíno","Radio Taíno", R.drawable.radio_taino),
           EmisoraModel("https://icecast.teveo.cu/b3jbfThq","Radio Reloj","Radio Reloj", R.drawable.ic_radioreloj),
           EmisoraModel("https://icecast.teveo.cu/9Rnrbjzq","Radio Enciclopedia","Radio Enciclopedia", R.drawable.radio_enciclopedia),
           EmisoraModel("https://icecast.teveo.cu/dXhtHs4P","Radio Caribe","Isla de la Juventud", R.drawable.radio_isla),
           EmisoraModel("https://icecast.teveo.cu/ngcdcV3k","Radio Guamá","Pinar del Río", R.drawable.radio_guama),
           EmisoraModel("https://icecast.teveo.cu/9HzjRcjX","Radio Artemisa","Artemisa", R.drawable.radio_artemisa),
           EmisoraModel("https://icecast.teveo.cu/McW3fLhs","Radio Habana Cuba","Radio Habana Cuba", R.drawable.radio_habanacuba),
           EmisoraModel("https://icecast.teveo.cu/fvc4RVRz","Radio Coco","La Habana", R.drawable.radio_coco),
           EmisoraModel("https://icecast.teveo.cu/Rsrm7P9h","Radio Mayabeque","Mayabeque", R.drawable.radio_mayabeque),
           EmisoraModel("https://icecast.teveo.cu/LsxKNz7b","Radio 26","Matanzas", R.drawable.radio_matanzas),
           EmisoraModel("https://icecast.teveo.cu/TsxMM94R","Radio Santa Clara","Villa Clara", R.drawable.radio_santaclara),
           EmisoraModel("https://icecast.teveo.cu/CL7jRXqn","Radio ciudad del Mar","Cienfuegos", R.drawable.radio_cienfuegos),
           EmisoraModel("https://icecast.teveo.cu/NqWrgw7j","Radio Sancti Spíritus","Sancti Spíritus", R.drawable.radio_santispiritus),
           EmisoraModel("https://icecast.teveo.cu/srJ4vqkv","Radio Caibarién","Sancti Spíritus", R.drawable.radio_caibarien),
           EmisoraModel("https://icecast.teveo.cu/F9tgnJVT","Radio Surco","Ciego de Ávila", R.drawable.radio_surco),
           EmisoraModel("https://icecast.teveo.cu/j99xztkT","Radio Cadena Agramonte","Camagüey", R.drawable.radio_cadenaagramonte),
           EmisoraModel("https://icecast.teveo.cu/3gnbjHVF","Radio Camagüey","Camagüey", R.drawable.radio_camaguey),
           EmisoraModel("https://icecast.teveo.cu/gXgMFKp9","Radio Guaimaro","Guaimaro, Camagüey", R.drawable.radio_guaimaro),
           EmisoraModel("https://icecast.teveo.cu/P77NJX4X","Radio Victoria","Las Tunas", R.drawable.radio_victoria),
           EmisoraModel("https://icecast.teveo.cu/9RLhkmRH","Radio Granma","Granma", R.drawable.radio_granma),
           EmisoraModel("https://icecast.teveo.cu/7hdNcTbM","Radio Bayamo CMKX","Bayamo, Granma", R.drawable.radio_bayamo),
           EmisoraModel("https://icecast.teveo.cu/dn9JTKtH","Radio Holguín CMKE","Holguín", R.drawable.radio_holguin),
           EmisoraModel("https://icecast.teveo.cu/C9vVPN7h","Radio CMKC","Santiago de Cuba", R.drawable.radio_santiago),
           EmisoraModel("https://icecast.teveo.cu/srfcdPC7","Radio Baracoa","Baracoa, Guantanamo", R.drawable.radio_baracoa),
           EmisoraModel("https://icecast.teveo.cu/PKWhw37L","Radio Bahía","Guantanamo", R.drawable.radio_bahia)
        )
    }
}