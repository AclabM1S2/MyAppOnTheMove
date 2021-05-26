package com.pdarcas.myapponthemove.data.entities

import kotlinx.serialization.Serializable
import org.osmdroid.util.GeoPoint
import java.util.stream.Collector
import java.util.stream.Collectors

@Serializable
class RecordModel(
    val id: String = "id",
    val name: String = "date",
    var points: List<GeoPointOTM>?,
    val idUser: String?
) {
    override fun toString(): String {
        return "RecordModel(id='$id', name='$name', points=$points, idUser='$idUser')"
    }

    fun setGeoPoints(points: ArrayList<GeoPoint>) {
        this.points = points
            .map {
                GeoPointOTM(it.longitude, it.latitude, it.altitude)
            }
    }

    fun getGeoPoints(): List<GeoPoint>? {
        return this.points
            ?.map {
                GeoPoint(it.latitude, it.longitude, it.altitude)
            }
    }


    @Serializable
    class GeoPointOTM() {

        var longitude: Double = 0.0
        var latitude: Double = 0.0
        var altitude: Double = 0.0

        constructor(longitude: Double, latitude: Double) : this() {
            this.longitude = longitude
            this.latitude = latitude
        }

        constructor(longitude: Double, latitude: Double, altitude: Double) : this() {
            this.longitude = longitude
            this.latitude = latitude
            this.altitude = altitude
        }
    }
}

