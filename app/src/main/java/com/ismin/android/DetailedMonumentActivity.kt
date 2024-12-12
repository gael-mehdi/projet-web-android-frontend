package com.ismin.android

import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailedMonumentActivity : AppCompatActivity() {

    // Création d'une instance de MonumentManagement pour accéder à la liste des monuments
    private val monumentManagement = MonumentManagement()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_monument)

        // Récupérer le ref passé par l'intention
        val monumentDetails = intent.getSerializableExtra("EXTRA_MONUMENT") as Monument

        try {
            // Mettre à jour les TextViews avec les détails du monument
            findViewById<TextView>(R.id.r_monument_detailed_txv_edif).text = monumentDetails.edif
            findViewById<TextView>(R.id.r_monument_detailed_txv_ref).text = "Référence : ${monumentDetails.ref}"
            findViewById<TextView>(R.id.r_monument_detailed_txv_dep_current_code).text = "Code département : ${monumentDetails.dep_current_code}"
            findViewById<TextView>(R.id.r_monument_detailed_txv_dep_name).text = "Nom du département : ${monumentDetails.dep_name}"
            findViewById<TextView>(R.id.r_monument_detailed_txv_com).text = "Commune : ${monumentDetails.com}"
            findViewById<TextView>(R.id.r_monument_detailed_txv_adresse).text = "Adresse : ${monumentDetails.adresse}"

            // Charger l'image depuis l'URL avec Glide
            val imageView = findViewById<ImageView>(R.id.r_monument_detailed_img)
            val imageUrl = monumentDetails.video_v // Assure-toi que tu as un champ `imageUrl` dans ton objet Monument

            // Utiliser Glide pour charger l'image
            Glide.with(this)
                .load(imageUrl) // URL de l'image
                .into(imageView) // Afficher l'image dans l'ImageView

            // Initialiser la CheckBox pour les favoris
            val favoriteCheckBox = findViewById<CheckBox>(R.id.r_monument_detailed_checkbox_favorite)
            favoriteCheckBox.isChecked = monumentDetails.favorite // Mettre l'état initial du favori

            // Gérer les changements d'état de la CheckBox
            favoriteCheckBox.setOnCheckedChangeListener { _, _ ->
                // Inverser l'état du favori
                monumentDetails.favorite = !monumentDetails.favorite // Inverser l'état local du favori

                // Mettre à jour le statut du favori via l'API ou localement
                updateFavoriteStatus(monumentDetails)
            }


        } catch (e: Exception) {
            // Si le monument n'est pas trouvé, afficher un message d'erreur
            showError("Monument introuvable")
        }
    }

    // Retrofit instance et service API
    private val retrofit = Retrofit.Builder()
        .baseUrl(SERVER_BASE_URL) // Remplacez par l'URL de votre API
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val monumentService: MonumentService = retrofit.create(MonumentService::class.java)

    // Fonction pour mettre à jour l'état du favori via l'API
    private fun updateFavoriteStatus(monument: Monument) {
        monumentService.updateFavoriteStatus(monument.ref).enqueue(object : Callback<Monument> {
            override fun onResponse(call: Call<Monument>, response: Response<Monument>) {
                if (response.isSuccessful) {
                    println("Favori mis à jour avec succès pour ${monument.ref}")
                    response.body()?.let {
                        // Mettre à jour l'état local si nécessaire
                        monument.favorite = it.favorite
                    }
                } else {
                    println("Erreur lors de la mise à jour du favori : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Monument>, t: Throwable) {
                println("Échec de la mise à jour du favori pour ${monument.ref} : ${t.message}")
            }
        })
    }

    // Méthode pour afficher une erreur
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}