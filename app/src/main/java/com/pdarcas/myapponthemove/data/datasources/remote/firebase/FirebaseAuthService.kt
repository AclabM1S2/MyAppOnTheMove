package com.pdarcas.myapponthemove.data.datasources.remote.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthService {

    private val auth: FirebaseAuth = Firebase.auth

    fun getCurrentFirebaseUser(): FirebaseUser? = auth.currentUser

    fun signIn(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)

    fun signUp(email: String, password: String) = auth.createUserWithEmailAndPassword(email, password)

    fun resetPassword(email: String) = auth.sendPasswordResetEmail(email)

    fun signOut() = auth.signOut()
}