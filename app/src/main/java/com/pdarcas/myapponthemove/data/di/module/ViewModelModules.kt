package com.pdarcas.myapponthemove.data.di.module

import com.pdarcas.myapponthemove.data.repositories.AuthRepository
import com.pdarcas.myapponthemove.ui.activities.authentication.AuthenticationViewModel
import com.pdarcas.myapponthemove.ui.fragments.login.LoginViewModel
import com.pdarcas.myapponthemove.ui.fragments.register.RegisterViewModel
import org.koin.dsl.module

object ViewModelModules {
    val viewModels = module {
        fun createLoginViewModel(authRepository: AuthRepository) = LoginViewModel(authRepository)
        fun createRegisterViewModel(authRepository: AuthRepository) = RegisterViewModel(authRepository)
        fun createAuthenticationViewModel(authRepository: AuthRepository) = AuthenticationViewModel(authRepository)

        single {
            createLoginViewModel(get())
            createRegisterViewModel(get())
            createAuthenticationViewModel(get())
        }
    }
}