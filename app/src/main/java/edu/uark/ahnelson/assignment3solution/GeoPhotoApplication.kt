package edu.uark.ahnelson.assignment3solution

import android.app.Application
import edu.uark.ahnelson.assignment3solution.Repository.GeoPhotosRepository
import edu.uark.ahnelson.assignment3solution.Repository.GeoPhotosRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GeoPhotoApplication:Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { GeoPhotosRoomDatabase.getDatabase(this,applicationScope)}
    val repository by lazy{ GeoPhotosRepository(database.geoPhotosDao())}
}