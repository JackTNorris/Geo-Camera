package edu.uark.ahnelson.assignment3solution.MainActivity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.osmdroid.config.Configuration.*


import edu.uark.ahnelson.assignment3solution.GeoPhotoApplication
import edu.uark.ahnelson.assignment3solution.R
import edu.uark.ahnelson.assignment3solution.Util.replaceFragmentInActivity
import org.osmdroid.util.GeoPoint

class MapsActivity : AppCompatActivity() {

    private lateinit var mapsFragment: OpenStreetMapFragment

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("MapsActivity","Permission Granted")
            } else {
                Toast.makeText(this,"Location Permissions not granted. Location disabled on map",Toast.LENGTH_LONG).show()
            }
        }


    private val mapsViewModel: MapsViewModel by viewModels {
        MapsViewModel.ToDoListViewModelFactory((application as GeoPhotoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)


        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener{
            takeNewPhoto()
        }

        //Get preferences for tile cache
        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        //Check for location permissions
        checkForLocationPermission()

        //Get access to mapsFragment object
        mapsFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                    as OpenStreetMapFragment? ?:OpenStreetMapFragment.newInstance().also{
                        replaceFragmentInActivity(it,R.id.fragmentContainerView)
        }

        //Begin observing data changes
        mapsViewModel.allGeoPhoto.observe(this){
            geoPhotos->
            geoPhotos.let {
                for(photo in geoPhotos){
                    val latitude = photo.value.latitude
                    val longitude = photo.value.longitude
                    val id = photo.value.id
                    var geoPoint:GeoPoint? = null

                    if(latitude!=null){
                        if(longitude!= null){
                            geoPoint = GeoPoint(latitude,longitude)
                        }
                    }
                    if(id != null && geoPoint!= null){
                        mapsFragment.addMarker(geoPoint,id)
                    }
                }
            }
        }
    }

    private fun takeNewPhoto(){
        //mapsFragment.clearMarkers()
        mapsFragment.clearOneMarker(25)
    }

    private fun checkForLocationPermission(){
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                //getLastKnownLocation()
                //registerLocationUpdateCallbacks()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

}