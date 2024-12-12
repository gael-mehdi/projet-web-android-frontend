package com.ismin.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val MONUMENTS = "monuments"

class MonumentListFragment : Fragment() {
    private lateinit var monuments: ArrayList<Monument>
    private lateinit var modifiedFavorites: MutableList<Monument> // Pour suivre les modifications locales
    private lateinit var monumentAdapter: MonumentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var monumentService: MonumentService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Permet au fragment d'influer sur le menu
        arguments?.let {
            monuments = it.getSerializable(MONUMENTS) as ArrayList<Monument>
        }
        modifiedFavorites = mutableListOf()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_monument_list, container, false)

        // Initialiser Retrofit et le service
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        monumentService = retrofit.create(MonumentService::class.java)

        recyclerView = rootView.findViewById(R.id.f_monument_list_rcv_monuments)
        monumentAdapter = MonumentAdapter(monuments) { updatedMonument ->
            handleFavoriteChange(updatedMonument)
        }
        recyclerView.adapter = monumentAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        return rootView
    }

    // Dans votre fragment (ou activité)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println("OptionSelected 1")
        return when (item.itemId) {

            R.id.fab_sync_favorites -> {
                println("OptionsSelected2")
                // Appeler la méthode synchronizeFavorites() ici
                synchronizeFavorites()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleFavoriteChange(monument: Monument) {
        // Ajouter ou mettre à jour le monument dans la liste des modifications
        val existingIndex = modifiedFavorites.indexOfFirst { it.ref == monument.ref }
        if (existingIndex >= 0) {
            println("Maj liste favori")
            modifiedFavorites[existingIndex] = monument // Mettre à jour
        } else {
            modifiedFavorites.add(monument) // Ajouter
            println("Ajout Monument Listefavori")
        }
    }

    private fun synchronizeFavorites() {
        // Si aucun changement, rien à synchroniser
        if (modifiedFavorites.isEmpty()) {
            println("Liste vide")
            return
        }

        for (monument in modifiedFavorites) { // Pour chaque monument modifié, on appelle l'API pour mettre à jour le statut de favori
            monumentService.updateFavoriteStatus(monument.ref).enqueue(object : Callback<Monument> {
                override fun onResponse(
                    call: Call<Monument>,
                    response: Response<Monument>
                ) {
                    if (response.isSuccessful) {
                        println("Favori synchronisé pour ${monument.ref}") // Le favori a été synchronisé avec succès
                    } else {
                        println("Erreur lors de la synchronisation : ${response.code()}") // Gérer les erreurs
                    }
                }

                override fun onFailure(call: Call<Monument>, t: Throwable) {
                    println("Échec de la synchronisation pour ${monument.ref} : ${t.message}") // Gérer les erreurs de connexion
                }
            })
        }

        modifiedFavorites.clear()// Réinitialiser la liste des changements après la synchronisation
    }

    companion object {
        @JvmStatic
        fun newInstance(monuments: ArrayList<Monument>) =
            MonumentListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(MONUMENTS, monuments)
                }
            }
    }
}