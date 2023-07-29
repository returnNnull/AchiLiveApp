package com.example.achiliveapp.domain.cases

import com.example.achiliveapp.data.models.dto.CategoriesSchemeDTO
import com.example.achiliveapp.data.models.entities.CategorySchemeEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.domain.models.CategoryWithRating
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCategoryWithRatingById @Inject constructor(
    private val categoriesRepository: ModelsRepository<CategorySchemeEntity, CategoriesSchemeDTO, String>,
    private val getRatingForCategory: GetRatingForCategory,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(id: String, refresh: Boolean = false) = withContext(dispatcher) {
        val categoryTask = async {
            categoriesRepository.getAll(refresh)
                .first()
                .first {
                    it.id == id
                }
        }
        val ratingTask = async { getRatingForCategory(id) }
        val category = categoryTask.await()
        val rating = ratingTask.await()
        CategoryWithRating(category.id, category.name, category.about, category.img, rating)
    }
}