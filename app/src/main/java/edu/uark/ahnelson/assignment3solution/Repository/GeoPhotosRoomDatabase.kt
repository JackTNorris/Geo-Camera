package edu.uark.ahnelson.assignment3solution.Repository

import android.content.Context
import android.location.Location
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.LinkedList


// Annotates class to be a Room Database with a table (entity) of the ToDoItem class
@Database(entities = arrayOf(GeoPhoto::class), version = 1, exportSchema = false)
public abstract class GeoPhotosRoomDatabase : RoomDatabase() {

    abstract fun geoPhotosDao(): GeoPhotoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: GeoPhotosRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): GeoPhotosRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GeoPhotosRoomDatabase::class.java,
                    "geophotos_database"
                )
                    .addCallback(GeophotosRoomDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class GeophotosRoomDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.geoPhotosDao())
                }
            }
        }

        suspend fun populateDatabase(geoPhotoDao: GeoPhotoDao) {
            // Delete all content here.
            geoPhotoDao.deleteAll()

            // Add sample words.
            val junkData = getJunkData()
            for (latLng in junkData){
                geoPhotoDao.insert(GeoPhoto(null,null,latLng.value.latitude,
                    latLng.value.longitude,null,latLng.key))
            }
        }

        fun getJunkData(): Map<String,LatLng>{
            val locationList:MutableMap<String,LatLng> = HashMap<String,LatLng>()
            locationList["Montgomery"] = LatLng(32.377716,-86.300568)
            locationList["Juneau"] = LatLng(58.301598,-134.420212)
            locationList["Phoenix"] = LatLng(33.448143,-112.096962)
            locationList["Little Rock"] = LatLng(34.746613,-92.288986)
            locationList["Sacramento"] = LatLng(38.576668,-121.493629)
            locationList["Denver"] = LatLng(39.739227,-104.984856)
            locationList["Hartford"] = LatLng(41.764046,-72.682198)
            locationList["Dover"] = LatLng(39.157307,-75.519722)
            locationList["Honolulu"] = LatLng(21.307442,-157.857376)
            locationList["Tallahassee"] = LatLng(30.438118,-84.281296)
            locationList["Atlanta"] = LatLng(33.749027,-84.388229)
            locationList["Boise"] = LatLng(43.617775,-116.199722)
            locationList["Springfield"] = LatLng(39.798363,-89.654961)
            locationList["Indianapolis"] = LatLng(39.768623,-86.162643)
            locationList["Des Moines"] = LatLng(41.591087,-93.603729)
            locationList["Topeka"] = LatLng(39.048191,-95.677956)
            locationList["Frankfort"] = LatLng(38.186722,-84.875374)
            locationList["Baton Rouge"] = LatLng(30.457069,-91.187393)
            locationList["Augusta"] = LatLng(44.307167,-69.781693)
            locationList["Annapolis"] = LatLng(38.978764,-76.490936)
            locationList["Boston"] = LatLng(42.358162,-71.063698)
            locationList["Lansing"] = LatLng(42.733635,-84.555328)
            locationList["St. Paul"] = LatLng(44.955097,-93.102211)
            locationList["Jackson"] = LatLng(32.303848,-90.182106)
            locationList["Jefferson City"] = LatLng(38.579201,-92.172935)
            locationList["Helena"] = LatLng(46.585709,-112.018417)
            locationList["Lincoln"] = LatLng(40.808075,-96.699654)
            locationList["Carson City"] = LatLng(39.163914,-119.766121)
            locationList["Concord"] = LatLng(43.206898,-71.537994)
            locationList["Trenton"] = LatLng(40.220596,-74.769913)
            locationList["Santa Fe"] = LatLng(35.68224,-105.939728)
            locationList["Raleigh"] = LatLng(35.78043,-78.639099)
            locationList["Bismarck"] = LatLng(46.82085,-100.783318)
            locationList["Albany"] = LatLng(42.652843,-73.757874)
            locationList["Columbus"] = LatLng(39.961346,-82.999069)
            locationList["Oklahoma City"] = LatLng(35.492207,-97.503342)
            locationList["Salem"] = LatLng(44.938461,-123.030403)
            locationList["Harrisburg"] = LatLng(40.264378,-76.883598)
            locationList["Providence"] = LatLng(41.830914,-71.414963)
            locationList["Columbia"] = LatLng(34.000343,-81.033211)
            locationList["Pierre"] = LatLng(44.367031,-100.346405)
            locationList["Nashville"] = LatLng(36.16581,-86.784241)
            locationList["Austin"] = LatLng(30.27467,-97.740349)
            locationList["Salt Lake City"] = LatLng(40.777477,-111.888237)
            locationList["Montpelier"] = LatLng(44.262436,-72.580536)
            locationList["Richmond"] = LatLng(37.538857,-77.43364)
            locationList["Olympia"] = LatLng(47.035805,-122.905014)
            locationList["Charleston"] = LatLng(38.336246,-81.612328)
            locationList["Madison"] = LatLng(43.074684,-89.384445)
            locationList["Cheyenne"] = LatLng(41.140259,-104.820236)

            return locationList
        }
    }



}