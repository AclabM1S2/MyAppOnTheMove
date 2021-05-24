package com.pdarcas.myapponthemove.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val positionUser = MutableLiveData<Boolean>()
    val actionNaviguer = MutableLiveData<Boolean>()
    val actionCharger = MutableLiveData<Boolean>()

    fun actionPosition(position: Boolean) {
        positionUser.value = position
        Log.d("Shared","position go")
    }

    fun actionNaviguer(naviguer: Boolean) {
        actionNaviguer.value = naviguer
    }

    fun actionCharger(charger: Boolean) {
        actionCharger.value = charger
    }



}