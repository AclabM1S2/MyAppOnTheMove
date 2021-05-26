package com.pdarcas.myapponthemove.data.di.module

import android.content.Context
import com.pdarcas.myapponthemove.utils.LocationLiveData
import org.koin.dsl.module

object UtilsModule {

    val locationModule = module {
        fun createLocationLiveData(context: Context) = LocationLiveData(context)

        single { createLocationLiveData(get()) }
    }
}