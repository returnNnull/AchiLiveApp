package com.example.achiliveapp.auth.ui

open class AuthUiState {

    var blocked: Boolean = false

    class Loading(): AuthUiState()
    class Logged(): AuthUiState()
    class Error(var e: Exception): AuthUiState()
    class Empty(): AuthUiState()

    fun isLoading() = this is Loading

    companion object{

        fun empty(): AuthUiState {
            return Empty()
        }

        fun loading(): AuthUiState {
            val state = Loading()
            state.blocked = true
            return state
        }

        fun error(e: Exception): Error {
            return Error(e)
        }


    }

}