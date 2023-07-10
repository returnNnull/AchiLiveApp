package com.example.achiliveapp.auth.utils

import com.google.android.material.textfield.TextInputLayout


 fun TextInputLayout.getTextWithTrim(): String {
        return this.editText!!.text.toString().trim()
    }
