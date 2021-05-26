package com.pdarcas.myapponthemove.ui.fragments.register

import androidx.lifecycle.ViewModel
import com.pdarcas.myapponthemove.data.repositories.AuthRepository

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun signUp(email: String, password: String) = authRepository.signUp(email, password)
}