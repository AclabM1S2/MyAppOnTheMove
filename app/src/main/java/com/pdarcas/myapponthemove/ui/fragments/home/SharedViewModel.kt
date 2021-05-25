package com.pdarcas.myapponthemove.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var positionUser = MutableLiveData<Boolean>()
    var actionNaviguer = MutableLiveData<Boolean>()
    var actionCharger = MutableLiveData<Boolean>()


}