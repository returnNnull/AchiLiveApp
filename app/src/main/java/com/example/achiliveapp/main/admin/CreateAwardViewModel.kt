package com.example.achiliveapp.main.admin

import android.content.res.Resources.NotFoundException
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.firebase.*
import com.example.achiliveapp.main.achi.data.AwardScheme
import com.example.achiliveapp.share.SpinnerItem
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CreateAwardViewModel(private val id: String?) : ViewModel(), ViewModelProvider.Factory {

    private val imageCloud = FirebaseImageCloud()
    private val firebaseAwards = AwardSchemeDataSource()
    private val firebaseCategories = CategoryDataSource()


    private val _screenUiState = MutableStateFlow<ScreenUiState>(ScreenUiState.Started())
    val screenUiState = _screenUiState.asStateFlow()


    val spinnerCategoryUiState = SpinnerUiState()
    val awardUiState = AwardUiState()


    fun save() {
        viewModelScope.launch {
            try {
                _screenUiState.value = ScreenUiState.Loading()
                val uri = awardUiState.img.value!!
                val resultImg = imageCloud.save(uri, FirebaseImageCloud.Folder.AWARDS).getOrThrow()
                val categoryId = spinnerCategoryUiState.getSelectedItem().id
                val awardDto = awardUiState.toDTO().also {
                    it.img = resultImg.toString()
                    it.categoriesId = categoryId
                }
                firebaseAwards.insert(awardDto).getOrThrow()
                _screenUiState.value = ScreenUiState.Success()
            } catch (e: Exception) {
                _screenUiState.value = ScreenUiState.Error(e)
            }
        }
    }

    fun setImg(uri: Uri) {
        awardUiState.img.value = uri
    }



    private fun init() {
        viewModelScope.launch {
            try {
                _screenUiState.value = ScreenUiState.Loading()
                val spinnerTask = async {  loadSpinnerListAsync() }
                if (id != null) {
                    val award = firebaseAwards.getById(id).getOrThrow()
                    val spinnerCategoriesList = spinnerTask.await()
                    val selectedItem = spinnerCategoriesList.first {
                        it.id == award.categoriesId
                    }
                    updateSpinnerUiStates(spinnerCategoriesList, selectedItem)
                    updateAwardUiState(award)
                }

            } catch (e: Exception) {
                _screenUiState.value = ScreenUiState.Error(e)
            }
        }
    }

    private suspend fun loadSpinnerListAsync(): MutableList<SpinnerItem> {
            val categoriesList = firebaseCategories.getAll().getOrThrow()
            val spinnerList: MutableList<SpinnerItem> = toSpinnerItems(categoriesList)
            updateSpinnerUiStates(spinnerList, spinnerList[0])
            return spinnerList
    }

    private fun updateSpinnerUiStates(
        spinnerList: MutableList<SpinnerItem>,
        selectedItem: SpinnerItem
    ) {
        spinnerCategoryUiState.list.value = spinnerList
        spinnerCategoryUiState.selectedPosition.value = selectedItem.position
    }

    private fun toSpinnerItems(categoriesList: List<CategoriesSchemeDTO>): MutableList<SpinnerItem> {
        val spinnerList: MutableList<SpinnerItem> = mutableListOf()
        for (i in categoriesList.indices) {
            val item = categoriesList[i]
            spinnerList.add(SpinnerItem(item.id, item.name, i))
        }
        return spinnerList
    }

    private fun updateAwardUiState(award: AwardSchemeDTO) {
        awardUiState.update(
            award.id,
            award.name,
            award.about,
            Uri.parse(award.img),
            award.maxValue,
            award.type
        )
    }


    private fun AwardUiState.toDTO(): AwardSchemeDTO {
        val award = AwardSchemeDTO(
            name = name.value!!,
            about = about.value!!,
            img = img.value!!.toString(),
            maxValue = maxValue.value!!,
            type = type.value!!
        )
        award.id = id.value!!
        return award
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateAwardViewModel::class.java)) {
            val viewModel = CreateAwardViewModel(id)
            viewModel.init()
            return viewModel as T
        }
        throw NotFoundException("ViewModel ${this.javaClass.name} not found!")
    }


    data class SpinnerUiState(
        val selectedPosition: MutableLiveData<Int> = MutableLiveData(0),
        val list: MutableLiveData<List<SpinnerItem>> = MutableLiveData(listOf(SpinnerItem.createPlaceholder()))
    ) {
        fun getSelectedItem(): SpinnerItem {
            return list.value!![selectedPosition.value!!]
        }
    }

    data class AwardUiState(
        val id: MutableLiveData<String> = MutableLiveData(""),
        val name: MutableLiveData<String> = MutableLiveData(""),
        val about: MutableLiveData<String> = MutableLiveData(""),
        val img: MutableLiveData<Uri> = MutableLiveData(Uri.EMPTY),
        val maxValue: MutableLiveData<Int> = MutableLiveData(0),
        val type: MutableLiveData<Int> = MutableLiveData(0)
    ) {

        fun update(
            id: String,
            name: String,
            about: String,
            img: Uri,
            maxValue: Int,
            type: Int
        ) {
            this.id.value = id
            this.name.value = name
            this.about.value = about
            this.img.value = img
            this.maxValue.value = maxValue
            this.type.value = type
        }

    }


}