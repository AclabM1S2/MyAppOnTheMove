package com.pdarcas.myapponthemove.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var positionUser = MutableLiveData<Boolean>()
    var actionNaviguer = MutableLiveData<Boolean>()
    var actionCharger = MutableLiveData<Boolean>()


}