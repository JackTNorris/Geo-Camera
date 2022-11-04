package edu.uark.ahnelson.assignment3solution.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geophotos_table")
data class GeoPhoto(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name="filename") var filename:String?,
    @ColumnInfo(name="latitude") var latitude:Double?,
    @ColumnInfo(name="longitude") var longitude:Double?,
    @ColumnInfo(name="datetime") var datetime:Double?,
    @ColumnInfo(name="extra") var extraInfo:String?
)