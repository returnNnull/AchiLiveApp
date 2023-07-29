package com.example.achiliveapp.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.achiliveapp.data.models.entities.CategorySchemeEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ICategoriesSchemeDAO : IBaseDAO<CategorySchemeEntity> {

    @Query("select * from CategorySchemeEntity")
    fun getAll(): Flow<List<CategorySchemeEntity>>

    @Query("select * from CategorySchemeEntity where id = :id")
    suspend fun getById(id: String): CategorySchemeEntity

    @Query("delete from CategorySchemeEntity")
    suspend fun deleteAll();
}