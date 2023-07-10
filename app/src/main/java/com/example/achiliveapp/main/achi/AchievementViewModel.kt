package com.example.achiliveapp.main.achi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.firebase.CategoriesSchemeDTO
import com.example.achiliveapp.firebase.CategoryDataSource
import com.example.achiliveapp.main.states.ListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AchievementViewModel : ViewModel() {

    private val _itemsState: MutableStateFlow<ListUiState<CategoriesSchemeDTO>> =
        MutableStateFlow(ListUiState.Loading())
    val itemState = _itemsState.asStateFlow()

    private val categoriesFirebaseDataSource = CategoryDataSource()

    init {
        viewModelScope.launch {
            try {
                val result = categoriesFirebaseDataSource.getAll().getOrThrow()
                _itemsState.value = ListUiState.Success(result)
            } catch (e: Exception) {
                _itemsState.value = ListUiState.Error(e)
            }
        }
    }


}