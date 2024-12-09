package com.ismin.android

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class MonumentAdapter(private var monuments: List<Monument>) : RecyclerView.Adapter<MonumentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonumentViewHolder {
        val rowView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_monument, parent, false)
        return MonumentViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: MonumentViewHolder, position: Int) {
        val monument = monuments[position]
        holder.txvRef.text = "ref: ${monument.ref}"
        holder.txvEdif.text = monument.edif
        // holder.txvAdresse.text = monument.adresse ?: "Adresse non disponible"
        holder.txvCom.text = monument.com
        // holder.txvLeg.text = monument.leg
        // holder.txvVideoV.text = monument.video_v
        // holder.txvGeoloc.text = monument.geoloc?.let { "lon: ${it.lon}, lat: ${it.lat}" } ?: "Localisation non disponible"
        // holder.txvRegName.text = monument.reg_name
        // holder.txvDepCurrentCode.text = monument.dep_current_code
        // holder.txvDepName.text = monument.dep_name
        // holder.txvFav.text = if (monument.fav) "Favoris : Oui" else "Favoris : Non"


        // Gérer le clic sur le bouton avec l'icône d'appareil photo
        holder.btnPhoto.setOnClickListener {
            // Créer une intention pour ouvrir un lien
            val url = monument.video_v // Supposons que chaque monument a une propriété `video_v`
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            // Démarrer l'activité sans vérification supplémentaire
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return monuments.size
    }

    fun updateMonuments(allMonuments: List<Monument>) {
        monuments = allMonuments
        notifyDataSetChanged()
    }
}