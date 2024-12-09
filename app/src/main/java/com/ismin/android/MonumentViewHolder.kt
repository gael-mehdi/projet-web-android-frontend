package com.ismin.android

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MonumentViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    var txvRef = rootView.findViewById<TextView>(R.id.r_monument_txv_ref)
    var txvEdif = rootView.findViewById<TextView>(R.id.r_monument_txv_edif)
    // var txvAdresse = rootView.findViewById<TextView>(R.id.r_monument_txv_adresse)
    var txvCom = rootView.findViewById<TextView>(R.id.r_monument_txv_com)
    // var txvLeg = rootView.findViewById<TextView>(R.id.r_monument_txv_leg)
    // var txvVideoV = rootView.findViewById<TextView>(R.id.r_monument_txv_videoV)
    // var txvGeoloc = rootView.findViewById<TextView>(R.id.r_monument_txv_geoloc)
    // var txvRegName = rootView.findViewById<TextView>(R.id.r_monument_txv_regName)
    // var txvDepCurrentCode = rootView.findViewById<TextView>(R.id.r_monument_txv_depCurrentCode)
    // var txvDepName = rootView.findViewById<TextView>(R.id.r_monument_txv_depName)
    // var txvFav = rootView.findViewById<TextView>(R.id.r_monument_txv_fav)
    val btnPhoto: ImageButton = rootView.findViewById(R.id.r_monument_btn_photo)
}