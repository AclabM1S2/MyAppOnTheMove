package com.pdarcas.myapponthemove.data.di.module

import android.content.Context
import org.koin.android.ext.koin.androidContext
import com.pdarcas.myapponthemove.utils.LocationLiveData
import org.koin.core.KoinApplication
import org.koin.dsl.module

object UtilsModule {

    val locationModule = module{
        single { LocationLiveData(androidContext())}
    }
}