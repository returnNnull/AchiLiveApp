package com.example.achiliveapp.main.admin

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.data.api.firebase.*
import com.example.achiliveapp.data.models.dto.AwardSchemeDTO
import com.example.achiliveapp.data.models.dto.CategoriesSchemeDTO
import com.example.achiliveapp.data.models.dto.RatingDTO
import com.example.achiliveapp.data.models.entities.AwardSchemeEntity
import com.example.achiliveapp.data.models.entities.AwardType
import com.example.achiliveapp.data.models.entities.CategorySchemeEntity
import com.example.achiliveapp.data.models.entities.RatingEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.share.SpinnerItem
import com.example.achiliveapp.share.states.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateAwardViewModel @Inject constructor(
    private val imageCloud: FirebaseImageCloud,
    private  val awardsRepository: ModelsRepository<AwardSchemeEntity, AwardSchemeDTO, String>,
    private val categoryRepository: ModelsRepository<CategorySchemeEntity, CategoriesSchemeDTO, String>,
    private val ratingRepository: ModelsRepository<RatingEntity, RatingDTO, String>
) : ViewModel(), ViewModelProvider.Factory {


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
                val awardDto = awardUiState.toEntity().also {
                    it.img = resultImg
                    it.categoriesId = categoryId
                }
                val award = awardsRepository.insert(awardDto)
                ratingRepository.insert(RatingEntity(awardSchemeId = award.id, categorySchemeId = categoryId))
                _screenUiState.value = ScreenUiState.Success()
            } catch (e: Exception) {
                _screenUiState.value = ScreenUiState.Error(e)
            }
        }
    }

    fun setImg(uri: Uri) {
        awardUiState.img.value = uri
    }



    fun init(id: String? = null) {
        viewModelScope.launch {
            try {
                _screenUiState.value = ScreenUiState.Loading()
                val spinnerTask = async {  loadSpinnerListAsync() }
                val spinnerCategoriesList = spinnerTask.await()
                if (id != null) {
                    val award = awardsRepository.getById(id)
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
            val categoriesList = categoryRepository.getAll(false).first()
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

    private fun toSpinnerItems(categoriesList: List<CategorySchemeEntity>): MutableList<SpinnerItem> {
        val spinnerList: MutableList<SpinnerItem> = mutableListOf()
        for (i in categoriesList.indices) {
            val item = categoriesList[i]
            spinnerList.add(SpinnerItem(item.id, item.name, i))
        }
        return spinnerList
    }

    private fun updateAwardUiState(award: AwardSchemeEntity) {
        awardUiState.update(
            award.id,
            award.name,
            award.about,
            award.img,
            award.maxValue,
            award.type.value
        )
    }


    private fun AwardUiState.toEntity(): AwardSchemeEntity {
        return AwardSchemeEntity(
            id = id.value!!,
            name = name.value!!,
            about = about.value!!,
            img = img.value!!,
            maxValue = maxValue.value!!,
            type = AwardType.getByInt(type.value!!)
        )
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