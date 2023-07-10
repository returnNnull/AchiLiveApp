package com.example.achiliveapp.auth.utils

open class EditTextUiException(message: String) : Exception(message){

    companion object{
        fun emptyField(): Exception{
            return EditTextUiException("Поле не может быть пустым")
        }

        fun lengthError(length: Int): EditTextUiException{
            return EditTextUiException("Поле должно содержать минимум $length символов")
        }
    }



}

class EmailEditTextException(message: String) : EditTextUiException(message)
{
    companion object{
        fun notEmailPattern(): EmailEditTextException {
            return EmailEditTextException("Почта введена не корректно")
        }
    }
}



class PasswordEditTextException(message: String): EditTextUiException(message){
    companion object{
        fun notEqualsPasswordWithRepeatPassword(): PasswordEditTextException{
            return  PasswordEditTextException("Пароли не совпадают")
        }
    }
}