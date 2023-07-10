package com.example.achiliveapp.main.admin

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class SelectImageGalleryUtil(private val registry:  ActivityResultRegistry) : DefaultLifecycleObserver {


    private var isSuccessCallback: ((Uri) -> Unit)? = null
    private var isErrorCallback: ((Exception) -> Unit)? = null

    private val resultCallback: ActivityResultCallback<Uri?> =
        ActivityResultCallback { result ->
            if (result != null) {
                isSuccessCallback!!(result)
            } else {
                isErrorCallback?.let {
                    it(Exception("Image uri is null"))
                }
            }
        }



    override fun onCreate(owner: LifecycleOwner) {

    }



    fun select() {

        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    fun isSuccess(isSuccessCallback: (Uri) -> Unit): SelectImageGalleryUtil {
        this.isSuccessCallback = isSuccessCallback
        return this
    }

    fun isError(isErrorCallback: (Exception) -> Unit): SelectImageGalleryUtil {
        this.isErrorCallback = isErrorCallback
        return this
    }

}
