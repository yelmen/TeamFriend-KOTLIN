package com.yelmen.varmisin

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class HaritalarActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_haritalar)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(dinleyici)

        /* val sydney = LatLng(-34.0, 151.0)
         mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
         mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener=object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                mMap.clear()
                val guncelKonum=LatLng(p0.latitude,p0.longitude)
                mMap.addMarker(MarkerOptions().position(guncelKonum).title("güncel konumunuz"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(guncelKonum,15f))
                val geocoder= Geocoder(this@HaritalarActivity, Locale.getDefault())
                try {
                    var adreslistesi= geocoder.getFromLocation(p0.latitude,p0.longitude,1)
                    if (adreslistesi.size>0){
                        println(adreslistesi.get(0).toString())
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

        }
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,10f,locationListener)
            val sonbilinenkonum=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if(sonbilinenkonum!=null){
                val sonbilinenlatlng=LatLng(sonbilinenkonum.latitude,sonbilinenkonum.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sonbilinenlatlng,15f))
                mMap.addMarker(MarkerOptions().position(sonbilinenlatlng).title("son bilinen konumunuz"))

            }

        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==1){
            if (grantResults.size>0){
                if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,10f,locationListener)
                }
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    val dinleyici=object :GoogleMap.OnMapLongClickListener {
        override fun onMapLongClick(p0: LatLng) {
            mMap.clear()
            val geocoder= Geocoder(this@HaritalarActivity, Locale.getDefault())
            if (p0!=null){
                var adres=""
                try {
                    val adresListesi=geocoder.getFromLocation(p0.latitude,p0.longitude,1)
                    if(adresListesi.size>0){
                        if (adresListesi.get(0).thoroughfare!=null){
                            adres+= adresListesi.get(0).thoroughfare
                            if (adresListesi.get(0).subThoroughfare!=null){
                                adres+=adresListesi.get(0).subThoroughfare
                            }
                        }

                    }

                }catch (e:java.lang.Exception){
                    e.printStackTrace()
                }
                mMap.addMarker(MarkerOptions().position(p0).title(adres))
            }
        }

    }
}