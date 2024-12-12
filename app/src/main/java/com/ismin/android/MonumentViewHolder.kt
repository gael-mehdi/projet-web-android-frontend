package com.ismin.android

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MonumentViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    var txvRef = rootView.findViewById<TextView>(R.id.r_monument_txv_ref)
    var txvEdif = rootView.findViewById<TextView>(R.id.r_monument_txv_edif)
    var txvCom = rootView.findViewById<TextView>(R.id.r_monument_txv_com)
    var txvDepCurrentCode = rootView.findViewById<TextView>(R.id.r_monument_txv_dep_current_code)
    val btnPhoto: ImageButton = rootView.findViewById(R.id.r_monument_btn_photo)
    val imgFavorite: ImageView = rootView.findViewById(R.id.r_monument_img_favorite)
}