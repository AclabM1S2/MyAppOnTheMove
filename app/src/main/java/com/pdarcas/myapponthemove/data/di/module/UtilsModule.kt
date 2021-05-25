package com.pdarcas.myapponthemove.data.di.module

import com.pdarcas.myapponthemove.ui.fragments.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import com.pdarcas.myapponthemove.utils.LocationLiveData
import org.koin.dsl.module

object UtilsModule {

    val locationModule = module{
        fun createHomeViewModel(liveData: LocationLiveData) = HomeViewModel(liveData)

        single { createHomeViewModel(LocationLiveData(androidContext()))}
    }
}