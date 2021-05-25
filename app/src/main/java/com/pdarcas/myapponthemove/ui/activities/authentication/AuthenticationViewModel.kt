package com.pdarcas.myapponthemove.ui.activities.authentication

import androidx.lifecycle.ViewModel
import com.pdarcas.myapponthemove.data.repositories.AuthRepository

class AuthenticationViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun getCurrentFirebaseUser() = authRepository.getCurrentFirebaseUser()
}