package com.example.achiliveapp.data.db.source

import com.example.achiliveapp.data.db.dao.IAwardSchemeDAO
import com.example.achiliveapp.data.models.entities.AwardSchemeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AwardSchemeLocalSource @Inject constructor(
    private val dao: IAwardSchemeDAO
) : LocalSource<AwardSchemeEntity, String>() {

    private val items = dao.getAll()

    override fun getAll(): Flow<List<AwardSchemeEntity>> {
        return items
    }

    override suspend fun deleteAll() = withContext(dispatcher){
        dao.deleteAll()
    }

    override suspend fun delete(e: AwardSchemeEntity)  = withContext(dispatcher) {
        dao.delete(e)
    }

    override suspend fun update(e: AwardSchemeEntity)  = withContext(dispatcher) {
        dao.insert(e)
    }

    override suspend fun insert(e: AwardSchemeEntity)  = withContext(dispatcher) {
        dao.insert(e)
    }

    override suspend fun getById(id: String): AwardSchemeEntity  = withContext(dispatcher) {
        dao.getById(id)
    }


}