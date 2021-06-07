package com.pdarcas.myapponthemove.data.entities

import com.google.firebase.firestore.DocumentSnapshot
import org.osmdroid.util.GeoPoint

data class RecordModel(
    val id: String,
    val name: String,
    val points: List<GeoPoint> = listOf(),
    val idUser: String?
) {

    companion object {
        fun fromFirebase(document: DocumentSnapshot): RecordModel =
            RecordModel(
                document["id"] as String,
                document["name"] as String,
                toGeoPoints(document.data?.get("points") as List<Map<String, Any>>),
                document["idUser"] as String
            )

        private fun toGeoPoints(geoPointsMap: List<Map<String, Any>>): List<GeoPoint> =
            geoPointsMap
                .map {
                    GeoPoint(
                        it["latitude"] as Double,
                        it["longitude"] as Double,
                    )
                }
    }
}

class WayPoints(
    var waypoints: ArrayList<GeoPoint> = ArrayList<GeoPoint>()
)