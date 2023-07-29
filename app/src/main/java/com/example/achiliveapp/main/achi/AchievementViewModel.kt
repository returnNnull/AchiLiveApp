package com.example.achiliveapp.main.achi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.data.api.firebase.AwardSchemeDataSource
import com.example.achiliveapp.data.api.firebase.CategoryDataSource
import com.example.achiliveapp.data.api.firebase.RatingDataSource
import com.example.achiliveapp.data.models.entities.AwardSchemeEntity
import com.example.achiliveapp.domain.cases.GetAwardsForPreview
import com.example.achiliveapp.domain.cases.GetCategoriesWithRating
import com.example.achiliveapp.domain.models.AwardsSchemeForPreview
import com.example.achiliveapp.domain.models.CategoryWithRating
import com.example.achiliveapp.main.states.ListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    private val getCategoriesWithRating: GetCategoriesWithRating,
    private val getAwardsForPreview: GetAwardsForPreview
) : ViewModel() {


    private val _categoriesListState: MutableStateFlow<ListUiState<CategoryWithRating>> =
        MutableStateFlow(ListUiState.Loading())
    val categoriesListState = _categoriesListState.asStateFlow()

    private val _awardsListState: MutableStateFlow<ListUiState<AwardsSchemeForPreview>> =
        MutableStateFlow(ListUiState.Loading())
    val awardsListState = _awardsListState.asStateFlow()


    init {
        viewModelScope.launch {
            launch{
                initCategoryList()
            }
            launch(Dispatchers.Default) {
                initAwardsList()
            }
        }
    }

    private suspend fun initCategoryList() = coroutineScope {
        try {
            getCategoriesWithRating().collect{
                _categoriesListState.value = ListUiState.Success(it)
            }

        } catch (e: Exception) {
            _categoriesListState.value = ListUiState.Error(e)
        }
    }

    private suspend fun initAwardsList() = coroutineScope {

        try {
            getAwardsForPreview(true).collect{
                _awardsListState.value = ListUiState.Success(it)
            }

        } catch (e: Exception) {
            _awardsListState.value = ListUiState.Error(e)
        }
    }






}