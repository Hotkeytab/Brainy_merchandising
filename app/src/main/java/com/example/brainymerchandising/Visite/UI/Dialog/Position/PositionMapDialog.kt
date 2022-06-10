package com.example.brainymerchandising.Visite.UI.Dialog.Position

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.DialogFragment
import com.example.brainymerchandising.R
import com.example.brainymerchandising.Visite.UI.MainVisiteFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.dialog_position_map.*


@AndroidEntryPoint
class PositionMapDialog(
      latitude: Double,
      longitude: Double,
       name: String ):DialogFragment(),OnMapReadyCallback
{

    private lateinit var mMap: GoogleMap
    private var latIn = latitude
    private var longIn = longitude
    private var nameIn = name



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_position_map, container, false)
    }
    override fun onStart() {
        super.onStart()

        val width = (resources.displayMetrics.widthPixels * 0.99).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.88).toInt()
        dialog!!.window?.setLayout(width, height)
        dialog!!.window!!.setWindowAnimations(R.style.AnimationsForDialog)
    }

    override fun onDestroy() {
        super.onDestroy()
        MainVisiteFragment.StaticMapClicked.mapIsRunning = false}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancel.setOnClickListener {
            dismiss()
        }

        store_name.text = nameIn


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_dialog) as SupportMapFragment

        mapFragment.getMapAsync(this)

    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Market and move the camera
        val marketPosition = LatLng(latIn, longIn)
        mMap.addMarker(MarkerOptions().position(marketPosition).title(nameIn))

        val location = CameraUpdateFactory.newLatLngZoom(
            marketPosition, 13f
        )
        mMap.animateCamera(location)
    }

}