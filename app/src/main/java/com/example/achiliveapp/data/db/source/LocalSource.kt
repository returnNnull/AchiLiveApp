package com.example.achiliveapp.data.db.source

import com.example.achiliveapp.data.models.entities.BaseEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow


abstract class LocalSource<E : BaseEntity<I>, I> constructor(protected val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    abstract fun getAll(): Flow<List<E>>
    abstract suspend fun getById(id : I): E
    abstract suspend fun insert(e : E)
    abstract suspend fun update(e : E)
    abstract suspend fun delete(e: E)
    abstract suspend fun deleteAll()

}