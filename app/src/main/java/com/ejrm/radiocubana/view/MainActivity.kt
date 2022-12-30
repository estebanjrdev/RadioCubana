package com.ejrm.radiocubana.view

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.AudioAttributes
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AlertDialog
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejrm.radiocubana.R
import com.ejrm.radiocubana.databinding.ActivityMainBinding
import com.ejrm.radiocubana.databinding.ContactoBinding
import com.ejrm.radiocubana.model.EmisoraModel
import com.ejrm.radiocubana.model.EmisoraProvider
import com.ejrm.radiocubana.services.RadioService
import com.ejrm.radiocubana.util.MediaPlayerSingleton
import com.ejrm.radiocubana.view.adapters.EmisoraAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var mediaPlayer: MediaPlayerSingleton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniRecyclerView()
if(checkForInternet(this)){
    Snackbar.make(binding.root, "Is Connected", 3000).show()
    if(isInternetReachable("https://apklis.cu/")){
        Snackbar.make(binding.root, "Data Connected", 3000).show()
    } else Snackbar.make(binding.root, "no hay datos", 3000).show()
} else Snackbar.make(binding.root, "Internet Not Connected", 3000).show()
       /* if (isNetworkAvailable(this)) {
            if (isOnlineNet()) {
                iniRecyclerView()
            } else {
                Toast.makeText(this, "No hay conexion", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "active wifi o datos moviles", Toast.LENGTH_SHORT).show()
        }*/

      /*  if(isInternetReachable()){
            Toast.makeText(this, "ACCECIBLE", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No disponible", Toast.LENGTH_SHORT).show()
        }*/

    }

    private fun iniRecyclerView() {
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = EmisoraAdapter(EmisoraProvider.getEmisoras()) { emisora ->
            itemSelected(
                emisora
            )
        }
    }
     fun isServiceRunning(mClass: Class<RadioService>): Boolean{

         val manager: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
         for (service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)){
             if (mClass.name.equals(service.service.className)){
                 return true
             }
         }
         return  false
     }
    fun itemSelected(emisora: EmisoraModel) {
        val url = emisora.link
       // initMediaPlayer(emisora.name, url, emisora.imagen)
        Intent(this,RadioService::class.java).also {
            it.putExtra("URL",url)
            it.putExtra("NAME",emisora.name)
            it.putExtra("IMAGE",emisora.imagen)
            startService(it)
            binding.layoutReproduction.visibility = LinearLayout.VISIBLE
            binding.imagelogo.setImageResource(emisora.imagen)
            binding.title.text = title
            binding.title.isSelected = true
            binding.btnPlay.setImageResource(R.drawable.ic_pause_24)
        }


    }

    fun startStopService(){
        if (isServiceRunning(RadioService::class.java)){

        }

    }

    private fun initMediaPlayer(title: String, url: String, imagen: Int) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayerSingleton
            mediaPlayer?.initMediaPlayerSingleton(this)
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            mediaPlayer?.setScreenOnWhilePlaying(true)
            mediaPlayer?.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            mediaPlayer?.prepareAsync()
            mediaPlayer?.setOnPreparedListener {
                binding.layoutReproduction.visibility = LinearLayout.VISIBLE
                binding.imagelogo.setImageResource(imagen)
                binding.title.text = title
                binding.title.isSelected = true
                binding.btnPlay.setImageResource(R.drawable.ic_pause_24)
                mediaPlayer?.start()
            }
        } else if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer = null
            binding.layoutReproduction.visibility = LinearLayout.INVISIBLE
            mediaPlayer = MediaPlayerSingleton
            mediaPlayer?.initMediaPlayerSingleton(this)
                mediaPlayer?.setDataSource(url)
                mediaPlayer?.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                mediaPlayer?.prepareAsync()
                mediaPlayer?.setOnPreparedListener {

                    binding.layoutReproduction.visibility = LinearLayout.VISIBLE
                    binding.imagelogo.setImageResource(imagen)
                    binding.title.text = title
                    binding.title.isSelected = true
                    binding.btnPlay.setImageResource(R.drawable.ic_pause_24)
                    mediaPlayer?.start()
                }

        }
        binding.btnPlay.setOnClickListener(View.OnClickListener {
            if (!isServiceRunning(RadioService::class.java)){
                startService(Intent(this,RadioService::class.java))
                binding.btnPlay.setImageResource(R.drawable.ic_pause_24)
            } else{
                stopService(Intent(this,RadioService::class.java))
                binding.btnPlay.setImageResource(R.drawable.ic_play_24)
            }

           /* if (mediaPlayer!!.isPlaying) {
                mediaPlayer?.pause()
                binding.btnPlay.setImageResource(R.drawable.ic_play_24)
            } else {
                mediaPlayer?.start()
                binding.btnPlay.setImageResource(R.drawable.ic_pause_24)
            }*/
        })
        binding.btnStop.setOnClickListener(View.OnClickListener {
          /*  if (mediaPlayer != null) {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer = null
                binding.layoutReproduction.visibility = LinearLayout.INVISIBLE
            }*/
        })
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET])
  suspend  fun isInternetReachable(url: String): Boolean {
        delay(2000)
        val httpConnection: HttpURLConnection =
            URL(url)
                .openConnection() as HttpURLConnection
        if (isNetworkAvailable(this)) {
            try {

                httpConnection.setRequestProperty("User-Agent", "Android")
                httpConnection.setRequestProperty("Connection", "close")
                httpConnection.connectTimeout = 1500
                httpConnection.connect()
                println(httpConnection.responseCode)

              //  return httpConnection.responseCode == 204
            } catch (e: Exception) {
              e.printStackTrace()
            }
        }
        return httpConnection.responseCode
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else @Suppress("deprecation") {
            val activeNetwork = connectivityManager.activeNetworkInfo ?: return false
            return activeNetwork.isConnectedOrConnecting
        }
    }

   /* val broadCastReceiver: BroadcastReceiver(){

    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.info -> {
                val alertdialog = AlertDialog.Builder(this)
                alertdialog.setTitle("Acerca de RadioCubana")
                alertdialog.setMessage(
                    "Esta aplicación nos permite escuchar las principales emisoras nacionales de radio desde el móvil.\n" +
                            "Requiere estar conectado a internet, pero solo consume del bono de los 300 MB nacionales.\n" +
                            "Siempre debe recordar que en caso de que habra alguna emisora y no cargue es porque hay emisoras que no trasmiten las 24 horas del día.\n" +
                            "Con RadioCubana podemos estar todo el tiempo informado de las últimas noticias, escuchar música y disfrutar de los partidos de beisbol de la serie nacional.\n" +
                            "La aplicación utiliza el icecast de teveo por lo que está sujeta a las políticas de privacidad de dicha plataforma."
                )
                alertdialog.setPositiveButton("Aceptar") { _, _ ->
                    finish()
                }
                alertdialog.show()
            }
            R.id.contact -> {
                val bindingcontact = ContactoBinding.inflate(layoutInflater)
                val alertdialog = AlertDialog.Builder(this)
                alertdialog.setTitle("Contacto")
                alertdialog.setView(bindingcontact.root)
                bindingcontact.layoutemail.setOnClickListener(View.OnClickListener {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.data = Uri.parse("Email")
                    val array_email = arrayOf("susoluciones.software@gmail.com")
                    intent.putExtra(Intent.EXTRA_EMAIL, array_email)
                    intent.putExtra(Intent.EXTRA_SUBJECT, "suSoluciones")
                    intent.putExtra(Intent.EXTRA_TEXT, "")
                    intent.type = "message/rfc822"
                    val a = Intent.createChooser(intent, "Launch Email")
                    startActivity(a)
                })
                bindingcontact.layoutshare.setOnClickListener(View.OnClickListener {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra(
                        "android.intent.extra.TEXT", "¡Hola!\n" +
                                " Te estoy invitando a que uses RadioCubana, con ella puedes escuchar las emisoras nacionales desde tu telefono\n" +
                                "\n" +
                                "Descárgala de: https://www.apklis.cu/application/com.ejrm.radiocubana"
                    )
                    intent.type = "text/plain"
                    startActivity(intent)
                })
                bindingcontact.layouttelegram.setOnClickListener(View.OnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://t.me/susoluciones")
                    startActivity(intent)
                })
                bindingcontact.layoutfacebook.setOnClickListener(View.OnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://www.facebook.com/susoluciones")
                    startActivity(intent)
                })
                bindingcontact.layoutweb.setOnClickListener(View.OnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("http://susoluciones.125mb.com")
                    startActivity(intent)
                })
                alertdialog.setPositiveButton("Aceptar") { _, _ ->

                }
                alertdialog.show()
            }
            R.id.help -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun isOnlineNet(): Boolean {
        try {
            val p = Runtime.getRuntime().exec("ping -c 1 www.google.es")
            val `val` = p.waitFor()
            return `val` == 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun checkForInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
        //else Snackbar.make(binding.root, "Internet Not Connected", 3000).show()
    }
}