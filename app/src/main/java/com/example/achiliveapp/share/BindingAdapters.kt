package com.example.achiliveapp.share

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.achiliveapp.share.views.InfoCard
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("app:state")
fun editTextState(editText: TextInputLayout, editableTextViewState: EditableTextViewState) {
    editText.error = editableTextViewState.error
    editText.isEnabled = !editableTextViewState.block
    editText.isErrorEnabled = !editableTextViewState.isValid()


}

@BindingAdapter("app:list")
fun selectedItem(spinner: Spinner, list: List<SpinnerItem>){
    val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_dropdown_item, list)
    spinner.adapter = adapter
}


@BindingAdapter("android:visibility")
fun authState(view: View, visibility: Boolean) {
    view.isVisible = visibility
}

@BindingAdapter("app:_tittle")
fun tittleFieldBinding(card: InfoCard, value: String){
    card.setTittle(value)
}
