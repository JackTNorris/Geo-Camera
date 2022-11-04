package edu.uark.ahnelson.assignment3solution.Repository

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface GeoPhotoDao {

    @MapInfo(keyColumn = "id")
    @Query("SELECT * FROM geophotos_table order by id ASC")
    fun getGeoPhotos(): Flow<Map<Int, GeoPhoto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(geoPhoto: GeoPhoto)

    @Query("DELETE FROM geophotos_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM geophotos_table WHERE id = :id")
    suspend fun getGeoPhotoById(id:Int): GeoPhoto

    @Query("DELETE FROM geophotos_table WHERE id=:id")
    suspend fun deleteGeoPhotoById(id: Int)

    @Update
    suspend fun updateGeoPhoto(geoPhoto: GeoPhoto)


}