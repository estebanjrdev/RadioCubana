package com.ejrm.radiocubana.view

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ejrm.radiocubana.R
import com.ejrm.radiocubana.databinding.ActivityMainBinding
import com.ejrm.radiocubana.databinding.ContactoBinding
import com.ejrm.radiocubana.model.EmisoraModel
import com.ejrm.radiocubana.model.EmisoraProvider
import com.ejrm.radiocubana.services.RadioService
import com.ejrm.radiocubana.view.adapters.EmisoraAdapter
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniRecyclerView()
        binding.btnPlay.setOnClickListener(View.OnClickListener {
            if (RadioService.isPlaying()) {
                RadioService.controlPlay()
                binding.btnPlay.setImageResource(R.drawable.ic_play_24)
            } else {
                RadioService.controlPlay()
                binding.btnPlay.setImageResource(R.drawable.ic_pause_24)
            }
        })
        binding.btnStop.setOnClickListener(View.OnClickListener {
            RadioService.stopRadio()
            Intent(this, RadioService::class.java).also {
                stopService(it)
            }
            binding.layoutReproduction.visibility = LinearLayout.INVISIBLE
        })
    }

    private fun iniRecyclerView() {
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = EmisoraAdapter(EmisoraProvider.getEmisoras()) { emisora ->
            itemSelected(
                emisora
            )
        }
    }

    /* fun isServiceRunning(mClass: Class<RadioService>): Boolean{

         val manager: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
         for (service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)){
             if (mClass.name.equals(service.service.className)){
                 return true
             }
         }
         return  false
     }*/
    fun itemSelected(emisora: EmisoraModel) {
        Intent(this, RadioService::class.java).also {
            it.putExtra("URL", emisora.link)
            it.putExtra("NAME", emisora.name)
            it.putExtra("IMAGE", emisora.imagen)
            startService(it)
            binding.layoutReproduction.visibility = LinearLayout.VISIBLE
            binding.imagelogo.setImageResource(emisora.imagen)
            binding.title.text = title
            binding.title.isSelected = true
            binding.btnPlay.setImageResource(R.drawable.ic_pause_24)
        }
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET])
    fun isInternetReachable(url: String): Int? {
        //delay(2000)
        var requestcode: Int? = null
        val httpConnection: HttpURLConnection =
            URL(url)
                .openConnection() as HttpURLConnection
        if (checkForInternet(this)) {
            try {
                httpConnection.setRequestProperty("User-Agent", "Android")
                httpConnection.setRequestProperty("Connection", "close")
                httpConnection.connectTimeout = 1500
                httpConnection.connect()
                //println(httpConnection.responseCode)
                requestcode = httpConnection.responseCode
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return requestcode
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

    fun checkForInternet(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

    override fun onResume() {
        super.onResume()
        registerReceiver(estadoRed, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private val estadoRed = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (checkForInternet(baseContext)) {
                val snackbar: Snackbar =
                    Snackbar.make(binding.idConstrain, "Is Connected", Snackbar.LENGTH_LONG)
                snackbar.show()
                /* if(isInternetReachable("https://apklis.cu/")){
                     Snackbar.make(binding.root, "Data Connected", 3000).show()
                 } else Snackbar.make(binding.root, "no hay datos", 3000).show()*/
            } else {
                val snackbar: Snackbar = Snackbar.make(
                    binding.idConstrain,
                    "Internet Not Connected",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.show()
            }
        }

    }
}