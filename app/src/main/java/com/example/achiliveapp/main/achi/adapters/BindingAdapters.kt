package com.example.achiliveapp.main.achi.adapters

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("app:imgUri", "app:error", "app:placeholder")
fun imageViewState(imageView: ImageView, uri: Uri, error: Drawable, placeholder: Drawable) {

    if (uri != Uri.EMPTY){
        Picasso.get().load(uri)
            .placeholder(placeholder)
            .error(error)
            .into(imageView)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
    }

}