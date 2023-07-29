package com.example.achiliveapp.data.db.source

import com.example.achiliveapp.data.db.dao.ICategoriesSchemeDAO
import com.example.achiliveapp.data.models.entities.CategorySchemeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoriesSchemeLocalSource @Inject constructor(
    private val dao: ICategoriesSchemeDAO

) : LocalSource<CategorySchemeEntity, String>() {


    override fun getAll(): Flow<List<CategorySchemeEntity>> {
        return dao.getAll()
    }

    override suspend fun deleteAll() = withContext(dispatcher) {
        dao.deleteAll()
    }

    override suspend fun delete(e: CategorySchemeEntity) = withContext(dispatcher){
        dao.delete(e)
    }

    override suspend fun update(e: CategorySchemeEntity) = withContext(dispatcher){
        dao.insert(e)
    }

    override suspend fun insert(e: CategorySchemeEntity) = withContext(dispatcher){
        dao.insert(e)
    }

    override suspend fun getById(id: String): CategorySchemeEntity = withContext(dispatcher){
        dao.getById(id)
    }
}