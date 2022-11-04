package edu.uark.ahnelson.assignment3solution.Repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class GeoPhotosRepository(private val geoPhotoDao: GeoPhotoDao) {


    val allGeophotos: Flow<Map<Int, GeoPhoto>> = geoPhotoDao.getGeoPhotos()


    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun insert(geoPhoto: GeoPhoto){
        geoPhotoDao.insert(geoPhoto)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun getGeoPhotoById(geoPhotoId: Int): GeoPhoto {
        return geoPhotoDao.getGeoPhotoById(geoPhotoId)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun deleteToDoItem(id: Int) {
        geoPhotoDao.deleteGeoPhotoById(id)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun updateGeoPhoto(geoPhoto: GeoPhoto) {
        geoPhotoDao.updateGeoPhoto(geoPhoto)
    }

}