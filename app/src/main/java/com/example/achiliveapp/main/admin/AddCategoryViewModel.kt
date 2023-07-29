package com.example.achiliveapp.main.admin

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.data.models.dto.CategoriesSchemeDTO
import com.example.achiliveapp.data.api.firebase.CategoryDataSource
import com.example.achiliveapp.data.api.firebase.FirebaseImageCloud
import com.example.achiliveapp.share.states.ScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCategoryViewModel : ViewModel() {

    private val imageCloud = FirebaseImageCloud()
    private val categoryDataSource = CategoryDataSource()


    private val _uiStates = MutableStateFlow<ScreenUiState>(ScreenUiState.Started())
    val uiStates = _uiStates.asStateFlow()

    val categoryUiState = CategoryUiState()


    fun save() {
        viewModelScope.launch {
            try {
                _uiStates.value = ScreenUiState.Loading()
                val uri = categoryUiState.uri.value!!
                val imgUploadUri = imageCloud.save(uri, FirebaseImageCloud.Folder.CATEGORIES).getOrThrow()
                val categoriesSchemeDTO = categoryUiState.toDTO()
                categoriesSchemeDTO.img = imgUploadUri.toString()
                categoryDataSource.insert(categoriesSchemeDTO).getOrThrow()
                _uiStates.value = ScreenUiState.Success()
            } catch (e: Exception) {
                _uiStates.value = ScreenUiState.Error(e)
            }
        }
    }

    fun initById(id: String?) {
        if (id != null) {
            viewModelScope.launch {
                try {
                    _uiStates.value = ScreenUiState.Loading()
                    val category = categoryDataSource.getById(id).getOrThrow()
                    categoryUiState.update(
                        category.id,
                        category.name,
                        category.about,
                        Uri.parse(category.img)
                    )
                    _uiStates.value = ScreenUiState.Success()
                } catch (e: Exception) {
                    _uiStates.value = ScreenUiState.Error(e)
                }
            }

        }

    }


    private fun CategoryUiState.toDTO(): CategoriesSchemeDTO {
        val categoriesSchemeDTO = CategoriesSchemeDTO(name.value!!, about.value!!, uri.value.toString())
        categoriesSchemeDTO.id = id.value!!
        return categoriesSchemeDTO
    }

    fun setImageUri(uri: Uri) {
        categoryUiState.uri.value = uri
    }


    data class CategoryUiState(
        val id: MutableLiveData<String> = MutableLiveData(""),
        val name: MutableLiveData<String> = MutableLiveData(""),
        val about: MutableLiveData<String> = MutableLiveData(""),
        val uri: MutableLiveData<Uri> = MutableLiveData(Uri.EMPTY)
    ) {
        fun update(id: String, name: String, about: String, uri: Uri) {
            this.id.value = id
            this.name.value = name
            this.about.value = about
            this.uri.value = uri
        }
    }
}