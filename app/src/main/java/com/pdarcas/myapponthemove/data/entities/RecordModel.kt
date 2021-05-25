package com.pdarcas.myapponthemove.data.entities

import org.osmdroid.util.GeoPoint

class RecordModel(
    val id: String= "id",
    val name: String = "date",
    val points: ArrayList<GeoPoint>,
    val idUser:String?
) {
    override fun toString(): String {
        return "RecordModel(id='$id', name='$name', points=$points, idUser='$idUser')"
    }
}