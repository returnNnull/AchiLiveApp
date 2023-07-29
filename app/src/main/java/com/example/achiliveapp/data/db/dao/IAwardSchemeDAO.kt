package com.example.achiliveapp.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.achiliveapp.data.models.entities.AwardSchemeEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface IAwardSchemeDAO : IBaseDAO<AwardSchemeEntity> {

    @Query("select * from AwardSchemeEntity")
    fun getAll(): Flow<List<AwardSchemeEntity>>

    @Query("select * from AwardSchemeEntity where id = :id")
    suspend fun getById(id: String): AwardSchemeEntity

    @Query("delete from AwardSchemeEntity")
    suspend fun deleteAll();

}