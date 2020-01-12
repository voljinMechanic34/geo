package com.example.geo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.geo.model.Example
import com.example.geo.model.Pin

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_selector.*
import java.util.ArrayList


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var example: Example
    var selectedItemList : ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolBar)
        setContentView(R.layout.activity_maps)

        val intent = intent
        if (intent.hasExtra("SelectedItems")){
            selectedItemList = intent.getStringArrayListExtra("SelectedItems")
        }


        button_forward.setOnClickListener{
            mMap.clear()
            val list  = example.services
            val intent = Intent(this,SelectorActivity::class.java)
            intent.putStringArrayListExtra("test", list as ArrayList<String>)
            startActivity(intent)
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        readFile()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Log.d("MaINaCTIV","ONCREATE")
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.clear()
        val googlePlex = CameraPosition.builder()
            .target(LatLng(55.7, 37.6))
            .zoom(11f)
            .bearing(0f)
            .tilt(45f)
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);
        addMarkers(mMap)
        // Add a marker in Sydney and move the camera
        /*val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
    }

    fun readFile(){
        val myJson = Utils.inputStreamToString(resources.openRawResource(R.raw.pins))
        example = Gson().fromJson(myJson,Example::class.java)
        Log.d("MyActivity",example.toString())
    }

   fun  addMarkers(mMap: GoogleMap) {
       val pins = example.pins

       for (pin in pins){
            if (selectedItemList.contains(pin.service))
            {
                val coordinates = pin.coordinates
                val id = pin.id.toString()
                val lat = coordinates.lat
                val lng = coordinates.lng

                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(lat, lng))
                        .title(id)
                        .snippet(id)
                        .rotation(33.5.toFloat())
                        .icon(BitmapDescriptorFactory.defaultMarker(pickColor(pin.service)))
                )
            }

       }

    }
    fun pickColor(color : String): Float {
        return when (color) {
            "a" -> BitmapDescriptorFactory.HUE_RED
            "b" -> BitmapDescriptorFactory.HUE_CYAN
            "c" -> BitmapDescriptorFactory.HUE_YELLOW

            else -> BitmapDescriptorFactory.HUE_AZURE
        }
    }
}
