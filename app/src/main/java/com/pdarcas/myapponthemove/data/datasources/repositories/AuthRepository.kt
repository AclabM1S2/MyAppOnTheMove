package com.pdarcas.myapponthemove.data.datasources.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.pdarcas.myapponthemove.data.datasources.remote.firebase.FirebaseAuthService

class AuthRepository(val firebaseAuthService: FirebaseAuthService) {

    fun getCurrentFirebaseUser() = firebaseAuthService.getCurrentFirebaseUser()

    fun signIn(email: String, password: String): MutableLiveData<FirebaseUser?> {
        val response = MutableLiveData<FirebaseUser?>()
        firebaseAuthService.signIn(email, password)
                .addOnSuccessListener {
                    response.value = it.user
                }.addOnFailureListener {

                }
        return response
    }

    fun signUp(email: String, password: String): MutableLiveData<FirebaseUser?> {
        val response = MutableLiveData<FirebaseUser?>()
        firebaseAuthService.signUp(email, password)
                .addOnSuccessListener {
                    response.value = it.user
                }.addOnFailureListener {
                    Log.d("ERREUR SignUp", it.message.toString())
                }
        return response
    }
}