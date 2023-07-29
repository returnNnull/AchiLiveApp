package com.example.achiliveapp.domain.cases

import com.example.achiliveapp.data.models.dto.AwardSchemeDTO
import com.example.achiliveapp.data.models.entities.AwardSchemeEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import com.example.achiliveapp.domain.models.AwardsSchemeForPreview
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAwardsForPreview @Inject constructor(
    private val awardsRepository: ModelsRepository<AwardSchemeEntity, AwardSchemeDTO, String>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(refresh: Boolean = true) = withContext(dispatcher) {
        awardsRepository.getAll(refresh).map {
            it.map { a ->
                async {
                    AwardsSchemeForPreview(a.id, a.name, a.about, a.img)
                }
            }.awaitAll()
        }
    }
}