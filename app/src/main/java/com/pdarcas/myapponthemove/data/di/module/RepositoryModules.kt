package com.pdarcas.myapponthemove.data.di.module

import com.pdarcas.myapponthemove.data.datasources.remote.firebase.FirebaseAuthService
import com.pdarcas.myapponthemove.data.repositories.AuthRepository
import org.koin.dsl.module

object RepositoryModules {
    val repositories = module {
        fun createAuthRepository(firebaseAuthService: FirebaseAuthService) = AuthRepository(firebaseAuthService)
        single { createAuthRepository(get()) }
    }
}