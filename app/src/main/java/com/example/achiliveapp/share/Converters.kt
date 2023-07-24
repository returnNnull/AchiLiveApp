package com.example.achiliveapp.share

import android.net.Uri
import android.widget.EditText
import androidx.databinding.InverseMethod

object Converter {

    @InverseMethod("intToString")
    @JvmStatic
    fun stringToString(value: String): Int {
        if (value.isEmpty()) return 0
        return value.trim().toInt()
    }

    @JvmStatic
    fun intToString(value: Int): String {
        return value.toString()
    }


}