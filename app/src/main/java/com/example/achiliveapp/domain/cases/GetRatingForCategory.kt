package com.example.achiliveapp.domain.cases

import com.example.achiliveapp.data.models.dto.RatingDTO
import com.example.achiliveapp.data.models.entities.RatingEntity
import com.example.achiliveapp.data.repositories.ModelsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRatingForCategory @Inject constructor(
    private val rating: ModelsRepository<RatingEntity, RatingDTO, String>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(id: String, refresh: Boolean = true) = withContext(dispatcher){
        val ratingTask = async { rating.getAll(refresh) }
        val ratingMap = ratingTask.await()
            .first()
            .groupBy {
                it.categorySchemeId
            }
        val ratingList = ratingMap[id]!!
        val count = async {  ratingList.sumOf { it.count }}
        val sum = async {  ratingList.sumOf { it.sum }}
        sum.await() / count.await()
    }

}