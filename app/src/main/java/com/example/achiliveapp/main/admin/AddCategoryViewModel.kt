package com.example.achiliveapp.main.admin

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.firebase.CategoriesSchemeDTO
import com.example.achiliveapp.firebase.CategoryDataSource
import com.example.achiliveapp.firebase.FirebaseImageCloud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class AddCategoryViewModel : ViewModel() {

    private val imageCloud = FirebaseImageCloud()
    private val categoryDataSource = CategoryDataSource()


    private val _uiStates = MutableStateFlow<UiStates>(UiStates.Ready())
    val uiStates = _uiStates.asStateFlow()

    private lateinit var uri: Uri


    fun create(name: String, about: String){
        viewModelScope.launch {
            try {
                _uiStates.value = UiStates.ImageUploading()
                val imgUploadResult = imageCloud.save(uri, FirebaseImageCloud.Folder.CATEGORIES)
                val imgUri = imgUploadResult.getOrThrow()
                _uiStates.value = UiStates.CategoryUploading()
                categoryDataSource.insert(CategoriesSchemeDTO(name, about, imgUri.toString())).getOrThrow()
                _uiStates.value = UiStates.Success()
            }
            catch (e: Exception){
                _uiStates.value = UiStates.Error(e)
            }
        }
    }

    fun setImageUri(uri: Uri){
        this.uri = uri
    }


    open class UiStates(){

        class Ready(): UiStates()
        class ImageUploading(val message: String = "Отправка изображения..."): UiStates()
        class CategoryUploading(val message: String = "Отправка категории..."): UiStates()
        class Error(val e: Exception) : UiStates()
        class Success() : UiStates()
    }


}