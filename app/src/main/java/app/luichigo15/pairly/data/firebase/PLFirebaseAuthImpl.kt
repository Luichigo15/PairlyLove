package app.luichigo15.pairly.data.firebase

import app.luichigo15.pairly.domain.firebase.PLFirebaseAuth
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import javax.inject.Inject

class PLFirebaseAuthImpl @Inject constructor() : PLFirebaseAuth {
    private val auth by lazy { Firebase.auth }

    override fun signIn() {
        if(auth.currentUser == null) auth.signInAnonymously()
    }
}