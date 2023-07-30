package com.example.achiliveapp.main.admin

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.data.models.dto.CategoriesSchemeDTO
import com.example.achiliveapp.data.api.firebase.CategorySchemeFirebase
import com.example.achiliveapp.data.api.firebase.FirebaseImageCloud
import com.example.achiliveapp.data.models.entities.CategorySchemeEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.share.states.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private  val firebaseImageCloud: FirebaseImageCloud,
    private val  categoriesRepository: ModelsRepository<CategorySchemeEntity, CategoriesSchemeDTO, String>
) : ViewModel() {


    private val _uiStates = MutableStateFlow<ScreenUiState>(ScreenUiState.Started())
    val uiStates = _uiStates.asStateFlow()

    val categoryUiState = CategoryUiState()


    fun save() {
        viewModelScope.launch {
            try {
                _uiStates.value = ScreenUiState.Loading()
                val uri = categoryUiState.uri.value!!
                val imgUploadUri = firebaseImageCloud.save(uri, FirebaseImageCloud.Folder.CATEGORIES).getOrThrow()
                val categoriesScheme = categoryUiState.toEntity()
                categoriesScheme.img = imgUploadUri
                categoriesRepository.insert(categoriesScheme)
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
                    val category = categoriesRepository.getById(id)
                    categoryUiState.update(
                        category.id,
                        category.name,
                        category.about,
                        category.img
                    )
                    _uiStates.value = ScreenUiState.Success()
                } catch (e: Exception) {
                    _uiStates.value = ScreenUiState.Error(e)
                }
            }

        }

    }


    private fun CategoryUiState.toEntity(): CategorySchemeEntity {
        return CategorySchemeEntity(id.value!!, name.value!!, about.value!!, uri.value!!, 5.0)
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