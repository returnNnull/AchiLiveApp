package com.example.achiliveapp.data.api.firebase

import com.example.achiliveapp.data.models.dto.RatingDTO
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatingFirebase : FirebaseDataSource<RatingDTO>() {


    override fun parse(v: DocumentSnapshot): RatingDTO? {
        return  v.toObject(RatingDTO::class.java)
    }

    override fun getTableName(): String {
        return RATING_DATA
    }

    suspend fun updateRating(awardSchemeId: String, grade: Int) = withContext(ioDispatcher){
        try {
            val rating = where("awardSchemeId", awardSchemeId).getOrThrow().first()
            rating.count++
            rating.sum += grade
            insert(rating)
        }
        catch (e: Exception){
            Result.failure(e)
        }


    }

}