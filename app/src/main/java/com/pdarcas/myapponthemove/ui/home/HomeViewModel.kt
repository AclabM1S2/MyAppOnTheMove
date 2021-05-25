package com.pdarcas.myapponthemove.ui.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pdarcas.myapponthemove.utils.LocationLiveData


class HomeViewModel(val location : LocationLiveData) : ViewModel(){
    var positionUser = MutableLiveData<Boolean>()
    var actionNaviguer = MutableLiveData<Boolean>()
    var actionCharger = MutableLiveData<Boolean>()
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun onInactive() = location.onInactive()

    fun onActive() = location.onActive()

    fun startLocationUpdates() = location.startLocationUpdates()



}