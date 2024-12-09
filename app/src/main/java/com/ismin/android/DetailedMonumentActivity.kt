package com.ismin.android

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
        } catch (e: Exception) {
            // Si le monument n'est pas trouvé, afficher un message d'erreur
            showError("Monument introuvable")
        }
    }

    // Méthode pour afficher une erreur (facultatif)
    private fun showError(message: String) {
        // Afficher un message d'erreur via un Toast
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}