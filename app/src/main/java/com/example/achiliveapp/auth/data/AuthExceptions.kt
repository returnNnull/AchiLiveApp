package com.example.achiliveapp.auth.data

import android.content.res.Resources
import com.example.achiliveapp.R

class AuthExceptions(message: String) : Exception(message) {

    companion object{
        fun userNotFound(): Exception{
            return AuthExceptions(Resources.getSystem().getString(R.string.userNotFoundException))
        }

        fun serverError(): AuthExceptions {
            return AuthExceptions("Ошибка сервера")
        }

    }

}

