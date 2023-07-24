package com.example.achiliveapp.auth.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.auth.data.AuthExceptions
import com.example.achiliveapp.auth.ui.AuthUiState
import com.example.achiliveapp.share.EditableTextViewState
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

    private val _emailUiState = MutableLiveData(EditableTextViewState.ready())
    val emailUiState: LiveData<EditableTextViewState> = _emailUiState

    private val _passUiState = MutableLiveData(EditableTextViewState.ready())
    val passUiState: LiveData<EditableTextViewState> = _passUiState


    fun login(email: String, pass: String) {
        if (validateEmail(email) && validatePassword(pass)){
            signInFirebase(email, pass)
        }
    }

    private fun validateEmail(email: String): Boolean {
        _emailUiState.value = EditableTextViewState.block()
        val emailValidationResult =
            TextValidatorBuilder().withEmpty().withEmailPattern().validate(email)
        if (emailValidationResult.isNotValid()) {
            _emailUiState.value = EditableTextViewState.error(emailValidationResult.error!!)
            return false
        }
        _emailUiState.value = EditableTextViewState.ready()
        return true
    }

    private fun validatePassword(pass: String): Boolean {
        _passUiState.value = EditableTextViewState.block()
        val passValidationResult = TextValidatorBuilder().withEmpty().withLength(6).validate(pass)
        if (passValidationResult.isNotValid()){
            _passUiState.value = EditableTextViewState.error(passValidationResult.error!!)
            return false
        }
        _passUiState.value = EditableTextViewState.ready()
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