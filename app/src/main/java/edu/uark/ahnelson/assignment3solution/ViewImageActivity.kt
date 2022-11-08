package edu.uark.ahnelson.assignment3solution

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.uark.ahnelson.assignment3solution.MainActivity.MapsViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


// activity for viewing an image
class ViewImageActivity: AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var currentPhotoPath: String


    private val mapsViewModel: MapsViewModel by viewModels {
        MapsViewModel.ToDoListViewModelFactory((application as GeoPhotoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_image_activity)
        imageView = findViewById(R.id.ivImageView)
        val id: Int? = intent.extras?.getString(ViewImageActivity.EXTRA_ID)?.toInt()
        lifecycleScope.launch {
            // asynchronously fetch geophoto from database and initialize view with values
            val geoPhoto = mapsViewModel.getGeoPhoto(id!!)
            findViewById<TextView>(R.id.description).setText(if (geoPhoto.extraInfo != null) geoPhoto.extraInfo else "No description" )
            findViewById<TextView>(R.id.location_taken).setText("Latitude: " + geoPhoto.latitude + " | Longitude: " + geoPhoto.longitude)
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS")
            val dateString = simpleDateFormat.format(geoPhoto.datetime)
            findViewById<TextView>(R.id.time_taken).setText(String.format("%s", dateString))
            currentPhotoPath = geoPhoto.filename!!
            setPic()
        }
    }

    // sets the picture in the view using the passed file reference
    private fun setPic() {
        // Get the dimensions of the View
        val targetW: Int = imageView.getWidth()
        val targetH: Int = imageView.getHeight()

        // Get the dimensions of the bitmap
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
        val scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        imageView.setImageBitmap(bitmap)
    }

    companion object {
        const val EXTRA_ID = "edu.uark.ahnelson.assignment3solution.ViewImageActivityStuff"
    }

}