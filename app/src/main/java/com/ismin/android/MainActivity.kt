package com.ismin.android

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Button

const val SERVER_BASE_URL = "https://app-d0436373-79f9-4739-a7e7-b74039970a4e.cleverapps.io" // URL de notre API

class MainActivity : AppCompatActivity(), MonumentCreator {

    private val monumentManagement = MonumentManagement()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()

    private val monumentService = retrofit.create(MonumentService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Charger les monuments au démarrage
        monumentService.getAllMonuments().enqueue(object : Callback<List<Monument>> {
            override fun onResponse(call: Call<List<Monument>>, response: Response<List<Monument>>) {
                val allMonuments: List<Monument>? = response.body()
                allMonuments?.forEach { monumentManagement.addMonument(it) }
                displayMonumentListFragment()
            }

            override fun onFailure(call: Call<List<Monument>>, t: Throwable) {
                Toast.makeText(baseContext, "Something wrong happened", Toast.LENGTH_SHORT).show()
            }
        })

        // Configurer les clics sur les boutons
        findViewById<Button>(R.id.button).setOnClickListener {
            // Afficher le fragment Liste
            displayFragment(MonumentListFragment.newInstance(monumentManagement.getAllMonuments()))
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            // Afficher le fragment Map
            displayFragment(MapFragment.newInstance(monumentManagement.getAllMonuments()))
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            // Afficher le fragment Info
            displayFragment(InfoFragment())
        }
    }

    private fun displayMonumentListFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val monumentListFragment = MonumentListFragment.newInstance(monumentManagement.getAllMonuments())
        fragmentTransaction.replace(R.id.a_main_lyt_container, monumentListFragment)
        fragmentTransaction.commit()
    }

    private fun displayMapFragment(monuments: List<Monument>?) {
        monuments?.let {
            val mapFragment = MapFragment.newInstance(it)
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.a_main_lyt_container, mapFragment)
            fragmentTransaction.addToBackStack(null) // Ajoute le fragment à la pile pour pouvoir revenir en arrière
            fragmentTransaction.commit()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {//menu 3 fragment
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {//clique sur un monument
        return when (item.itemId) {
            R.id.action_show_list -> {
                // Afficher le fragment de la liste
                displayFragment(MonumentListFragment.newInstance(monumentManagement.getAllMonuments()))
                true
            }
            R.id.action_show_map -> {
                // Afficher le fragment de la carte
                displayFragment(MapFragment.newInstance(monumentManagement.getAllMonuments())) // Passer les monuments
                true
            }

            R.id.action_show_info -> {
                // Afficher le fragment d'information de l'application
                displayFragment(InfoFragment())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.a_main_lyt_container, fragment)
        fragmentTransaction.addToBackStack(null) // Permet de revenir en arrière si nécessaire
        fragmentTransaction.commit()
    }


    override fun onMonumentCreated(monument: Monument) {
        monumentService.createMonument(monument)
            .enqueue {
                onResponse = {
                    val monumentFromServer: Monument? = it.body()
                    monumentManagement.addMonument(monumentFromServer!!)
                    displayMonumentListFragment()
                }
                onFailure = {
                    Toast.makeText(this@MainActivity, it?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

}