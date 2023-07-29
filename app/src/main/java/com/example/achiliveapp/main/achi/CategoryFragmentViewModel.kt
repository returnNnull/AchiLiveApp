package com.example.achiliveapp.main.achi

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.domain.cases.GetAwardsByCategoryId
import com.example.achiliveapp.domain.cases.GetCategoryWithRatingById
import com.example.achiliveapp.domain.models.AwardsSchemeForPreview
import com.example.achiliveapp.main.states.ListUiState
import com.example.achiliveapp.share.states.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryFragmentViewModel @Inject constructor(
    private val getAwardsByCategoryId: GetAwardsByCategoryId,
    private val getCategoryWithRatingById: GetCategoryWithRatingById
) : ViewModel(){

    private val _itemsState = MutableStateFlow<ListUiState<AwardsSchemeForPreview>>(ListUiState.Loading())
    val awardsListUiState = _itemsState.asStateFlow()

    private val _screenUiState = MutableStateFlow<ScreenUiState>(ScreenUiState.Started())
    val screenUiState = _screenUiState.asStateFlow()

    private val _categoryUiState = CategoryUiState()
    val categoryUiState = _categoryUiState.onlyRead()



    fun initWithId(categoryId: String) {
        viewModelScope.launch {
            launch{
                initAwardsListState(categoryId)
            }
            launch{
                initCategoryUiState(categoryId)
            }
        }
    }

    private suspend fun initCategoryUiState(categoryId: String) {
        try {
            val category = getCategoryWithRatingById(categoryId)
            _categoryUiState.img.value = category.img
            _categoryUiState.name.value = category.name
            _categoryUiState.about.value = category.about

        } catch (e: Exception) {
            _screenUiState.value = ScreenUiState.Error(e)
        }
    }

    private suspend fun initAwardsListState(categoryId: String) {
        try {
            val list = getAwardsByCategoryId(categoryId)
            _categoryUiState.count.value = list.size
            _itemsState.value = ListUiState.Success(list)
        } catch (e: Exception) {
            _itemsState.value = ListUiState.Error(e)
            _screenUiState.value = ScreenUiState.Error(e)
        }
    }



    inner class CategoryUiState(
        override val count: MutableStateFlow<Int> = MutableStateFlow(0),
        override val started: MutableStateFlow<Int> = MutableStateFlow(0),
        override val ended: MutableStateFlow<Int> = MutableStateFlow(0),
        override val img: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY),
        override val name: MutableStateFlow<String> = MutableStateFlow(""),
        override val about: MutableStateFlow<String> = MutableStateFlow("")
    ) : CategoryUiStateOnlyRead(count, started, ended, img, name, about) {

        fun onlyRead(): CategoryUiStateOnlyRead {
            return this
        }
    }

    abstract class CategoryUiStateOnlyRead(
        open val count: StateFlow<Int>,
        open val started: StateFlow<Int>,
        open val ended: StateFlow<Int>,
        open val img: StateFlow<Uri>,
        open val name: StateFlow<String>,
        open val about: StateFlow<String>
    )

}