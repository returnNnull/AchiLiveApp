package com.example.achiliveapp.main.admin

open class ScreenUiState {
    class Loading(): ScreenUiState()
    class Success(): ScreenUiState()
    class Error(): ScreenUiState()
}