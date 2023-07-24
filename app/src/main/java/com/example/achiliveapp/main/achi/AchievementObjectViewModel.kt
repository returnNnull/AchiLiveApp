package com.example.achiliveapp.main.achi

import android.content.res.Resources.NotFoundException
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.firebase.AwardSchemeDTO
import com.example.achiliveapp.firebase.AwardSchemeDataSource
import com.example.achiliveapp.firebase.CategoriesSchemeDTO
import com.example.achiliveapp.main.achi.data.AwardScheme
import com.example.achiliveapp.main.states.ListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AchievementObjectViewModel(private val categoryId: String) : ViewModel(), ViewModelProvider.Factory {

    private val _itemsState  = MutableStateFlow<ListUiState<AwardScheme>>(ListUiState.Loading())
    val itemState = _itemsState.asStateFlow()

    private val awardSchemeDataSource = AwardSchemeDataSource()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AchievementObjectViewModel::class.java)){
            val viewModel  = AchievementObjectViewModel(categoryId)
            viewModel.init(categoryId)
            return  viewModel as T
        }
        throw NotFoundException("AchievementListViewModel not found")

    }

    private fun init(categoryId: String) {
        viewModelScope.launch {
            try {
                val result = awardSchemeDataSource.where("categoriesId", categoryId).getOrThrow().map {
                    it.toDate()
                }
                _itemsState.value = ListUiState.Success(result)
            }
            catch (e: Exception){
                _itemsState.value = ListUiState.Error(e)
            }
        }
    }

    private fun AwardSchemeDTO.toDate(): AwardScheme{
        return  AwardScheme(
            id, name, about, Uri.parse(img), maxValue,  categoriesId)

    }

    fun update() {
        init(categoryId)
    }

}