package com.example.achiliveapp.domain.cases

import com.example.achiliveapp.data.models.dto.AwardSchemeDTO
import com.example.achiliveapp.data.models.entities.AwardSchemeEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.domain.models.AwardsSchemeForPreview
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAwardsByCategoryId @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val awardsRepository: ModelsRepository<AwardSchemeEntity, AwardSchemeDTO, String>
) {

    suspend operator fun invoke(categoryId: String, refresh: Boolean = false) = withContext(dispatcher){
        awardsRepository.getAll(refresh)
            .first()
            .filter{
                it.categoriesId == categoryId
            }
            .map { a ->
                async {
                    AwardsSchemeForPreview(a.id, a.name, a.about, a.img)
                }
            }.awaitAll()
    }
}