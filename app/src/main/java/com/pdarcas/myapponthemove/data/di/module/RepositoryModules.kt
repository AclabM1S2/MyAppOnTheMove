package com.pdarcas.myapponthemove.data.di.module

import android.content.Context
import com.pdarcas.myapponthemove.data.datasources.remote.firebase.FirebaseAuthService
import com.pdarcas.myapponthemove.data.repositories.AuthRepository
import com.pdarcas.myapponthemove.data.repositories.StorageRepository
import org.koin.dsl.module

object RepositoryModules {
    val repositories = module {
        fun createAuthRepository(firebaseAuthService: FirebaseAuthService) = AuthRepository(firebaseAuthService)
        fun createStorageRepository(context: Context) = StorageRepository(context)

        single { createAuthRepository(get()) }
        single { createAuthRepository(get()) }
    }
}