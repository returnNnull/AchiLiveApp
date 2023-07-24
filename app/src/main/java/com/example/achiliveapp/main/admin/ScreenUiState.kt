package com.example.achiliveapp.main.admin

open class ScreenUiState {
    class Loading(): ScreenUiState()
    class Success(val message: String = ""): ScreenUiState()
    class Error(val message: Exception): ScreenUiState()

    class Ready<T>(val t: T): ScreenUiState()

    class Started(): ScreenUiState()
}