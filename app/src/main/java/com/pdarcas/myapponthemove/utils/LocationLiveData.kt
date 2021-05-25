package com.pdarcas.myapponthemove.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


class LocationLiveData(context: Context) : LiveData<Location>() {

    var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

/*    public fun isActive(){
        return fusedLocationClient.applicationContext().
    }*/

    public override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)

    }

    @SuppressLint("MissingPermission")
    public override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.also {
                    setLocationData(it)
                }
            }
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }

    private fun setLocationData(location: Location) {
        value = location
    }

    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 3000
            /*smallestDisplacement=2000f*/
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }


}