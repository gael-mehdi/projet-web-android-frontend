package com.ismin.android

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val SERVER_BASE_URL = "https://app-d0436373-79f9-4739-a7e7-b74039970a4e.cleverapps.io" // URL de notre API

class MainActivity : AppCompatActivity(), MonumentCreator {

    private val monumentManagement = MonumentManagement()

    private val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL).build()
    private val monumentService = retrofit.create(MonumentService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        monumentService.getAllMonuments().enqueue(object : Callback<List<Monument>> {
            override fun onResponse(
                call: Call<List<Monument>>, response: Response<List<Monument>>
            ) {
                val allMonuments: List<Monument>? = response.body()
                allMonuments?.forEach { monumentManagement.addMonument(it) }
                displayMonumentListFragment()
            }

            override fun onFailure(call: Call<List<Monument>>, t: Throwable) {
                Toast.makeText(baseContext, "Something wrong happened", Toast.LENGTH_SHORT).show()
            }
        })
    }

        /*
        btnCreateMonument.setOnClickListener {
            displayCreateBookFragment()
        }
    }
    */

    private fun displayMonumentListFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val monumentListFragment = MonumentListFragment.newInstance(monumentManagement.getAllMonuments())
        fragmentTransaction.replace(R.id.a_main_lyt_container, monumentListFragment)
        fragmentTransaction.commit()
        // btnCreateBook.visibility = View.VISIBLE
    }

        /*
    private fun displayCreateBookFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val createBookFragment = CreateMonumentFragment()
        fragmentTransaction.replace(R.id.a_main_lyt_container, createBookFragment)
        fragmentTransaction.commit()
        btnCreateBook.visibility = View.GONE
    }
    */
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                monumentManagement.clear()
                displayMonumentListFragment()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
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