package com.example.achiliveapp.main.achi.adapters

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.constraintlayout.helper.widget.MotionPlaceholder
import androidx.databinding.BindingAdapter
import com.example.achiliveapp.auth.ui.components.EditTextState
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

@BindingAdapter("app:imgUri", "app:error", "app:placeholder")
fun imageViewState(imageView: ImageView, uri: String, error: Drawable, placeholder: Drawable) {
    Picasso.get().load(uri)
        .placeholder(placeholder)
        .error(error)
        .into(imageView)
}