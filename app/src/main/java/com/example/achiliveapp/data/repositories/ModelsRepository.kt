package com.example.achiliveapp.data.repositories

import com.example.achiliveapp.data.api.RemoteSource
import com.example.achiliveapp.data.db.source.LocalSource
import com.example.achiliveapp.data.models.dto.DTO
import com.example.achiliveapp.data.models.entities.BaseEntity
import com.example.achiliveapp.data.models.mapper.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ModelsRepository<E : BaseEntity, D : DTO, I : Any>(
    private var local: LocalSource<E, I>,
    private var remote: RemoteSource<D, I>,
    private var mapper: Mapper<E, D, I>,
) {

    private val dispatcherDefault: CoroutineDispatcher = Dispatchers.IO
    private val items = local.getAll()

    suspend fun getAll(refresh: Boolean) = withContext(dispatcherDefault) {
        if(refresh){
            refresh()
        }
        items
    }

    suspend fun refresh() = withContext(dispatcherDefault) {
        local.deleteAll()
        val list = remote.getAll().getOrThrow()
        for (i in list) {
            local.insert(mapper.toEntity(i))
        }
    }

    suspend fun insert(entity: E) = withContext(dispatcherDefault) {
        val result = remote.insert(mapper.toDto(entity)).getOrThrow()
        val e = mapper.toEntity(result)
        local.insert(e)
        e
    }

    suspend fun delete(entity: E) = withContext(dispatcherDefault) {
        val result = remote.delete(mapper.toDto(entity)).getOrThrow()
        local.delete(entity)
    }

    suspend fun update(entity: E) {
        val result = remote.update(mapper.toDto(entity)).getOrThrow()
        local.insert(entity)
    }

    suspend fun getById(id: I): E  = withContext(dispatcherDefault){
        local.getById(id)
    }

}