package com.example.achiliveapp.auth.ui.reg

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.auth.data.AuthExceptions
import com.example.achiliveapp.auth.ui.AuthUiState
import com.example.achiliveapp.share.EditableTextViewState
import com.example.achiliveapp.auth.utils.PasswordEditTextException
import com.example.achiliveapp.auth.utils.TextValidatorBuilder
import com.example.achiliveapp.firebase.ProfileDTO
import com.example.achiliveapp.firebase.ProfileDataSource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegistrationViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val profileDataSource = ProfileDataSource()

    private val _authState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.Empty())
    val authState = _authState.asStateFlow()

    private val _emailUiState = MutableLiveData(EditableTextViewState.ready())
    val emailUiState: LiveData<EditableTextViewState> = _emailUiState

    private val _passUiState = MutableLiveData(EditableTextViewState.ready())
    val passUiState: LiveData<EditableTextViewState> = _passUiState

    private val _repeatPassUiState = MutableLiveData(EditableTextViewState.ready())
    val repeatPassUiState: LiveData<EditableTextViewState> = _repeatPassUiState


    fun registration(email: String, pass: String, repeatPass: String) {
        if (pass != repeatPass) {
            _repeatPassUiState.value =
                EditableTextViewState.error(PasswordEditTextException.notEqualsPasswordWithRepeatPassword())
            return
        }
        if (validateEmail(email) && validatePassword(pass)) {
            registrationFirebase(email, pass)
        }
    }

    private fun registrationFirebase(email: String, pass: String) {
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, pass).await()
                if (result.user == null) {
                    throw AuthExceptions.serverError()
                }
                else{
                    val profileDTO = ProfileDTO()
                    profileDTO.id = result.user!!.uid
                    profileDataSource.insert(profileDTO).getOrThrow()
                }
            } catch (e: Exception) {
                _authState.value = AuthUiState.error(e)
            }
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
        if (passValidationResult.isNotValid()) {
            _passUiState.value = EditableTextViewState.error(passValidationResult.error!!)
            return false
        }
        _passUiState.value = EditableTextViewState.ready()
        return true
    }
}