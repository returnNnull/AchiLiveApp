package com.example.achiliveapp.data.db.source

import com.example.achiliveapp.data.db.dao.IRatingDAO
import com.example.achiliveapp.data.models.entities.RatingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatingLocalSource @Inject constructor(private val dao: IRatingDAO) :
    LocalSource<RatingEntity, String>() {


    override fun getAll(): Flow<List<RatingEntity>> {
        return dao.getAll()
    }

    override suspend fun deleteAll() = withContext(dispatcher) {
        dao.deleteAll()
    }

    override suspend fun delete(e: RatingEntity) = withContext(dispatcher) {
        dao.delete(e)
    }

    override suspend fun update(e: RatingEntity) = withContext(dispatcher) {
        dao.insert(e)
    }

    override suspend fun insert(e: RatingEntity) = withContext(dispatcher) {
        dao.insert(e)
    }

    override suspend fun getById(id: String): RatingEntity = withContext(dispatcher) {
        dao.getById(id)
    }
}