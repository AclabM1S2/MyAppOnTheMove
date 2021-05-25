package com.pdarcas.myapponthemove.data.repositories

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.pdarcas.myapponthemove.data.datasources.remote.firebase.FirebaseAuthService

class AuthRepository(private val firebaseAuthService: FirebaseAuthService) {

    fun getCurrentFirebaseUser() = firebaseAuthService.getCurrentFirebaseUser()

    fun signIn(email: String, password: String): MutableLiveData<FirebaseUser?> {
        val response = MutableLiveData<FirebaseUser?>()
        firebaseAuthService.signIn(email, password)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "connectUserWithEmail:success")
                response.value = it.user
            }.addOnFailureListener { task ->
                Log.w(ContentValues.TAG, "connectUserWithEmail:failure", task.cause)
                response.value = null
            }
        return response
    }

    fun signUp(email: String, password: String): MutableLiveData<FirebaseUser?> {
        val response = MutableLiveData<FirebaseUser?>()
        firebaseAuthService.signUp(email, password)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "createUserWithEmail:success")
                response.value = it.user
            }.addOnFailureListener { task ->
                Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.cause)
                response.value = null
            }
        return response
    }

    fun signOut() {
        firebaseAuthService.signOut()
    }
}