package com.example.achiliveapp.main.achi.adapters

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.achiliveapp.main.states.ListUiState
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["app:imgUri", "app:error", "app:placeholder"], requireAll = false)
fun imageViewState(imageView: ImageView, uri: Uri, error: Drawable, placeholder: Drawable) {


    if (uri != Uri.EMPTY) {
        Picasso.get().load(uri)
            .placeholder(placeholder)
            .error(error)
            .into(imageView)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
    }

}

@BindingAdapter("app:itemsList")
@Suppress("UNCHECKED_CAST")
fun <T> recyclerViewItemsBinding(recyclerView: RecyclerView, listUiState: ListUiState<T>) {
    try {
        val adapter = recyclerView.adapter as DefaultRecyclerViewAdapter<T>
        if (listUiState.isSuccess()){
            adapter.items = listUiState.data!!
        }
        if (listUiState is ListUiState.ItemChange<T>){
            adapter.notifyItemChanged(listUiState.position)
        }

        if (listUiState is ListUiState.ItemRemoved<T>){
            adapter.notifyItemRemoved(listUiState.position)
        }
    }
    catch (e: Exception){
        Log.e("APP_LOG","Recyclerview adapter not instance of DefaultRecyclerViewAdapter<T>")
    }
}


