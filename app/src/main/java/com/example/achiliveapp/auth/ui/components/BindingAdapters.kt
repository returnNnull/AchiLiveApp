package com.example.achiliveapp.auth.ui.components

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.achiliveapp.auth.ui.components.EditTextState
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("app:editTextState")
fun editTextState(editText: TextInputLayout, editTextState: EditTextState) {
    editText.error = editTextState.error
    editText.isEnabled = !editTextState.block
    editText.isErrorEnabled = !editTextState.isValid()
}

@BindingAdapter("android:visibility")
fun authState(view: View, visibility: Boolean) {
    view.isVisible = visibility
}
