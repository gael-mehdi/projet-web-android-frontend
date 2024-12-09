package com.ismin.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ismin.android.R

class MapFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private var mapReady = false
    private lateinit var monuments: List<Monument>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)
        arguments?.getSerializable("monuments")?.let {
            monuments = it as List<Monument>
        }
        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()

        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            mapReady = true
            updateMap() // Met à jour la carte une fois prête
        }

        return rootView
    }

    private fun updateMap() {
        if (mapReady && monuments.isNotEmpty()) {
            monuments.forEach { monument ->
                monument.geoloc?.let { geoloc ->
                    val position = LatLng(geoloc.lat, geoloc.lon)
                    mMap.addMarker(MarkerOptions().position(position).title(monument.edif))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position)) // Déplacer la caméra sur le premier marqueur
                }
            }
        }
    }

    companion object {
        fun newInstance(monuments: List<Monument>): MapFragment {
            val fragment = MapFragment()
            val bundle = Bundle()
            bundle.putSerializable("monuments", ArrayList(monuments)) // Passer la liste
            fragment.arguments = bundle
            return fragment
        }
    }
}