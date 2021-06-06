package com.pdarcas.myapponthemove.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pdarcas.myapponthemove.data.repositories.AuthRepository
import com.pdarcas.myapponthemove.utils.LocationLiveData


class HomeViewModel(val location : LocationLiveData, val authRepository: AuthRepository) : ViewModel(){
    var positionUser = MutableLiveData<Boolean>()
    var actionNaviguer = MutableLiveData<Boolean>()
    var actionCharger = MutableLiveData<Boolean>()
    fun getCurrentUser()=authRepository.getCurrentFirebaseUser()

}