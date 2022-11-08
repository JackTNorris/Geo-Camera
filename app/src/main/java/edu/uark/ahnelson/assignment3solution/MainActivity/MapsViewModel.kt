package edu.uark.ahnelson.assignment3solution.MainActivity

import androidx.lifecycle.*
import edu.uark.ahnelson.assignment3solution.Repository.GeoPhoto
import edu.uark.ahnelson.assignment3solution.Repository.GeoPhotosRepository
import kotlinx.coroutines.launch


class MapsViewModel(private val repository: GeoPhotosRepository): ViewModel() {

    val allGeoPhoto: LiveData<Map<Int, GeoPhoto>> = repository.allGeophotos.asLiveData()

    fun insert(geoPhoto: GeoPhoto) = viewModelScope.launch {
        repository.insert(geoPhoto)
    }

    suspend fun getGeoPhoto(geoPhotoId: Int): GeoPhoto {
        return repository.getGeoPhotoById(geoPhotoId)
    }

    class ToDoListViewModelFactory(private val repository: GeoPhotosRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(MapsViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MapsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }


}