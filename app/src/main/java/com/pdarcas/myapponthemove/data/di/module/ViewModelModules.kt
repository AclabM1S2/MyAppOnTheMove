package com.pdarcas.myapponthemove.data.di.module

import com.pdarcas.myapponthemove.data.repositories.AuthRepository
import com.pdarcas.myapponthemove.ui.activities.MainActivityViewModel
import com.pdarcas.myapponthemove.ui.activities.authentication.AuthenticationViewModel
import com.pdarcas.myapponthemove.ui.fragments.home.HomeViewModel
import com.pdarcas.myapponthemove.ui.fragments.login.LoginViewModel
import com.pdarcas.myapponthemove.ui.fragments.register.RegisterViewModel
import com.pdarcas.myapponthemove.utils.LocationLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModules {
    val viewModels = module {
        fun createLoginViewModel(authRepository: AuthRepository) = LoginViewModel(authRepository)
        fun createRegisterViewModel(authRepository: AuthRepository) = RegisterViewModel(authRepository)
        fun createAuthenticationViewModel(authRepository: AuthRepository) = AuthenticationViewModel(authRepository)
        fun createHomeViewModel(liveData: LocationLiveData,authRepository: AuthRepository) = HomeViewModel(liveData,authRepository)
        fun createMainActivityViewModel(authRepository: AuthRepository) = MainActivityViewModel(authRepository)

        viewModel {
            createLoginViewModel(get())
        }

        viewModel {
            createRegisterViewModel(get())
        }

        viewModel {
            createAuthenticationViewModel(get())
        }

        viewModel {
            createHomeViewModel(get(),get())
        }

        viewModel {
            createMainActivityViewModel(get())
        }
    }
}