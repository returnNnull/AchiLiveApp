package com.example.achiliveapp.auth.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.auth.data.AuthExceptions
import com.example.achiliveapp.auth.ui.AuthUiState
import com.example.achiliveapp.auth.ui.components.EditTextState
import com.example.achiliveapp.auth.utils.TextValidatorBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SigningViewModel : ViewModel() {

    private val auth = Firebase.auth

    private val _authState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.Empty())
    val authState = _authState.asStateFlow()

    private val _emailUiState = MutableLiveData(EditTextState.ready())
    val emailUiState: LiveData<EditTextState> = _emailUiState

    private val _passUiState = MutableLiveData(EditTextState.ready())
    val passUiState: LiveData<EditTextState> = _passUiState


    fun login(email: String, pass: String) {
        if (validateEmail(email) && validatePassword(pass)){
            signInFirebase(email, pass)
        }
    }

    private fun validateEmail(email: String): Boolean {
        _emailUiState.value = EditTextState.block()
        val emailValidationResult =
            TextValidatorBuilder().withEmpty().withEmailPattern().validate(email)
        if (emailValidationResult.isNotValid()) {
            _emailUiState.value = EditTextState.error(emailValidationResult.error!!)
            return false
        }
        _emailUiState.value = EditTextState.ready()
        return true
    }

    private fun validatePassword(pass: String): Boolean {
        _passUiState.value = EditTextState.block()
        val passValidationResult = TextValidatorBuilder().withEmpty().withLength(6).validate(pass)
        if (passValidationResult.isNotValid()){
            _passUiState.value = EditTextState.error(passValidationResult.error!!)
            return false
        }
        _passUiState.value = EditTextState.ready()
        return true
    }


    private fun signInFirebase(email: String, pass: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthUiState.loading()
                val result = auth.signInWithEmailAndPassword(email, pass).await()
                if (result.user == null) {
                    throw AuthExceptions.userNotFound()
                }
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(e)
            }

        }
    }


}