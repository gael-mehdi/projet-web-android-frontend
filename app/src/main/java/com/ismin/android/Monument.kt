package com.ismin.android

import java.io.Serializable

data class Monument(
    val ref: String, // Référence unique
    val edif: String, // Nom de l'édifice
    val adresse: String?, // Adresse si disponible
    val com: String, // Commune
    val leg: String, // Légende ou description
    val video_v: String, // URL vers la vidéo ou image principale
    val geoloc: Geoloc?, // Coordonnées géographiques si disponibles
    val reg_name: String, // Région
    val dep_current_code: String, // Numéro de département
    val dep_name: String, // Département
    val fav: Boolean = false // Favoris ou non (false par défaut)
) : Serializable

data class Geoloc(
    val lon: Double, // Longitude
    val lat: Double  // Latitude
) : Serializable