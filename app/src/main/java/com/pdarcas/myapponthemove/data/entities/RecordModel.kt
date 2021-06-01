package com.pdarcas.myapponthemove.data.entities

import org.osmdroid.util.GeoPoint

data class RecordModel(
    var id: String= "id",
    var name: String = "date",
    var points: ArrayList<GeoPoint>? = ArrayList<GeoPoint>(),
    var idUser:String? = "toto"
) {
    override fun toString(): String {
        return "RecordModel(id='$id', name='$name', points=$points, idUser='$idUser')"
    }
}

class WayPoints(
    var waypoints: ArrayList<GeoPoint> = ArrayList<GeoPoint>()
)