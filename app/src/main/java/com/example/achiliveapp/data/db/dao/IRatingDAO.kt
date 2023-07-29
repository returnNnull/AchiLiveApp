package com.example.achiliveapp.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.achiliveapp.data.models.entities.CategorySchemeEntity
import com.example.achiliveapp.data.models.entities.RatingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IRatingDAO : IBaseDAO<RatingEntity> {

    @Query("select * from RatingEntity")
    fun getAll(): Flow<List<RatingEntity>>

    @Query("select * from RatingEntity where id = :id")
    suspend fun getById(id: String): RatingEntity

    @Query("delete from RatingEntity")
    suspend fun deleteAll();
}