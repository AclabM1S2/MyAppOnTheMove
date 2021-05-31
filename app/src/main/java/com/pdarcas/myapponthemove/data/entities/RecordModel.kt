package com.pdarcas.myapponthemove.data.entities

import org.osmdroid.util.GeoPoint

data class RecordModel(
    var id: String= "id",
    var name: String = "date",
    //var points: ArrayList<GeoPoint> = listOf<GeoPoint>() as ArrayList<GeoPoint>,
    var idUser:String? = "toto"
) {
    override fun toString(): String {
        return "RecordModel(id='$id', name='$name', /points=, idUser='$idUser')"
    }
}