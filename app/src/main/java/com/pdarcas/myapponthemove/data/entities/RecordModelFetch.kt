package com.pdarcas.myapponthemove.data.entities

import org.osmdroid.util.GeoPoint

data class RecordModelFetch(
    var id: String= "id",
    var name: String = "date",
    var points: List<pts>? =listOf<pts>() as ArrayList<pts> ,
    var idUser:String? = "toto"
) {
    override fun toString(): String {
        return "RecordModel(id='$id', name='$name', points=$points, idUser='$idUser')"
    }
}
class pts(
    var latitude:Double =0.0,
    var longitude:Double=0.0,
)
class ListPts(
    var  points:List<pts> = listOf<pts>()
)