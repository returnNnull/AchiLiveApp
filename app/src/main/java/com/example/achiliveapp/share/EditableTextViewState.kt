package com.example.achiliveapp.share

class EditableTextViewState private constructor(){


    var error: String = DEFAULT_VALUE
    var block: Boolean = false
    var text: CharSequence = DEFAULT_VALUE

    fun  isValid(): Boolean = error == DEFAULT_VALUE


    companion object{

        const val DEFAULT_VALUE = ""
        fun error(e: Exception): EditableTextViewState {
            val state = EditableTextViewState()
            state.error = e.message ?: "Error"
            return state
        }

        fun ready(): EditableTextViewState {
            return EditableTextViewState()
        }

        fun block(): EditableTextViewState {
            val state = EditableTextViewState()
            state.block = true
            return state
        }
    }
}