package com.ismin.android

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MonumentAdapter(
    private var monuments: List<Monument>, // Liste des monuments
    private val onFavoriteChanged: (Monument) -> Unit // Callback déclenché lorsque le favori est modifié
) : RecyclerView.Adapter<MonumentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonumentViewHolder {
        val rowView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_monument, parent, false)
        return MonumentViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: MonumentViewHolder, position: Int) {
        val monument = monuments[position]

        holder.txvRef.text = monument.ref
        holder.txvEdif.text = monument.edif
        holder.txvDepCurrentCode.text = monument.dep_current_code
        holder.txvCom.text = monument.com

        // Mettre à jour le CheckBox en fonction de l'état local du favori
        holder.btnStar.isChecked = monument.favorite

        // Listener pour détecter les changements d'état local
        holder.btnStar.setOnCheckedChangeListener { _, isChecked ->
            monument.favorite = isChecked // Modifier l'état local
            onFavoriteChanged(monument)  // Informer le parent de la modification
        }

        // Clic sur le titre pour ouvrir les détails
        holder.txvEdif.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailedMonumentActivity::class.java)
            intent.putExtra("EXTRA_MONUMENT", monument)
            context.startActivity(intent)
        }

        // Clic sur le bouton photo
        holder.btnPhoto.setOnClickListener {
            val url = monument.video_v ?: "https://example.com" // URL par défaut si `video_v` est null
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
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