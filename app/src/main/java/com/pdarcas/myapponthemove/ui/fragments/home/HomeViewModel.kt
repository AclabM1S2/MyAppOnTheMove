package com.pdarcas.myapponthemove.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pdarcas.myapponthemove.utils.LocationLiveData


class HomeViewModel(val location : LocationLiveData) : ViewModel(){
    var positionUser = MutableLiveData<Boolean>()
    var actionNaviguer = MutableLiveData<Boolean>()
    var actionCharger = MutableLiveData<Boolean>()


}