package com.pdarcas.myapponthemove.data.di.module

import com.pdarcas.myapponthemove.data.datasources.remote.firebase.FirebaseAuthService
import org.koin.dsl.module

object ServiceModules {
    val services = module {
        fun createFirebaseAuthService() = FirebaseAuthService()
        single { createFirebaseAuthService() }
    }
}