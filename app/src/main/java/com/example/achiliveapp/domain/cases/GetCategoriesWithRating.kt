package com.example.achiliveapp.domain.cases

import com.example.achiliveapp.data.models.dto.CategoriesSchemeDTO
import com.example.achiliveapp.data.models.entities.CategorySchemeEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.domain.models.CategoryWithRating
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetCategoriesWithRating @Inject constructor(
    private val categories: ModelsRepository<CategorySchemeEntity, CategoriesSchemeDTO, String>,
    private val getRatingForCategory: GetRatingForCategory,

    ) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
    suspend operator fun invoke(refresh: Boolean = true) = withContext(dispatcher) {
        categories.getAll(refresh)
            .map {
                it.map { e ->
                        CategoryWithRating(
                            e.id,
                            e.name,
                            e.about,
                            e.img,
                            getRatingForCategory(e.id)
                        )

                }
            }
    }

    suspend fun refresh() = withContext(dispatcher){
        categories.refresh()
    }
}