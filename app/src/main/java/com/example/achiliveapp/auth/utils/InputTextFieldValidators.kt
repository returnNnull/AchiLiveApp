package com.example.achiliveapp.auth.utils

import android.util.Patterns

class TextValidatorBuilder(){

    private val validators: MutableList<TextValidator> = mutableListOf()

    fun withLength(length: Int): TextValidatorBuilder {
        validators.add(TextLengthValidator(length))
        return this
    }

    fun withEmpty() : TextValidatorBuilder{
        validators.add(TextEmptyValidator())
        return this
    }

    fun withEmailPattern() : TextValidatorBuilder {
        validators.add(EmailValidator())
        return this
    }

    fun validate(text: String): TextValidationResult{
        for (v in validators){
            v.validate(text)
            if (v.error != null){
                return TextValidationResult(v.error)
            }
        }
        return TextValidationResult()
    }

    class TextValidationResult(var error: Exception? = null){
        fun isValid(): Boolean = error == null
        fun isNotValid(): Boolean = error != null
    }
}

abstract class TextValidator() {
    var error: Exception? = null
    abstract fun validate(text: String)
}



class TextLengthValidator(private val length: Int) : TextValidator(){
    override fun validate(text: String) {
        if (text.length < length){
            error =  EditTextUiException.lengthError(length)
        }
    }
}

class TextEmptyValidator(): TextValidator(){
    override fun validate(text: String) {
        if (text.isEmpty() || text.isBlank()){
            error = EditTextUiException.emptyField()
        }
    }

}



class EmailValidator() : TextValidator(){
    override fun validate(text: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()){
            error = EmailEditTextException.notEmailPattern()
        }
    }
}





