package com.pdarcas.myapponthemove.data.di

import android.app.Application
import com.pdarcas.myapponthemove.data.di.module.UtilsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@BaseApplication)
            androidFileProperties()
            modules(
                listOf(
                    UtilsModule.locationModule
                )
            )
            koin.createRootScope()
        }
    }
}