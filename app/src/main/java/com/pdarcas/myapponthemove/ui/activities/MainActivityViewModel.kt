package com.pdarcas.myapponthemove.ui.activities

import androidx.lifecycle.ViewModel
import com.pdarcas.myapponthemove.data.repositories.AuthRepository

class MainActivityViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun signOut() = authRepository.signOut()
    fun getCurrentUser() = authRepository.getCurrentFirebaseUser()

}