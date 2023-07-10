package com.example.achiliveapp.auth.ui.components

class EditTextState private constructor(){

    private val DEFAULT_ERROR_VALUE = ""

    var error: String = DEFAULT_ERROR_VALUE
    var block: Boolean = false

    fun  isValid(): Boolean = error == DEFAULT_ERROR_VALUE


    companion object{

        fun error(e: Exception): EditTextState {
            val state = EditTextState()
            state.error = e.message ?: "Error"
            return state
        }

        fun ready(): EditTextState {
            return EditTextState()
        }

        fun block(): EditTextState {
            val state = EditTextState()
            state.block = true
            return state
        }
    }
}