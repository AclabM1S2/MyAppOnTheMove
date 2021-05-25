package com.pdarcas.myapponthemove.ui.fragments.login

import androidx.lifecycle.ViewModel
import com.pdarcas.myapponthemove.data.repositories.AuthRepository

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun signIn(email: String, password: String) = authRepository.signIn(email, password)

}