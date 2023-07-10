package com.example.achiliveapp

import android.content.res.Resources.NotFoundException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.achiliveapp.auth.ui.AuthUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel(), ViewModelProvider.Factory, FirebaseAuth.AuthStateListener {

    private val _authState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.Loading())
    val authState = _authState.asStateFlow()

    private val auth = Firebase.auth


    private fun init() {
        auth.addAuthStateListener(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            val vm = MainViewModel()
            vm.init()
            return vm as T
        }

        throw NotFoundException("MainViewModel not found")
    }

    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        if (firebaseAuth.currentUser != null){
            _authState.value = AuthUiState.Logged()
        }
        else{
            _authState.value = AuthUiState.Empty()
        }

    }

    fun remoteObservers(){
        auth.removeAuthStateListener(this)
    }


}