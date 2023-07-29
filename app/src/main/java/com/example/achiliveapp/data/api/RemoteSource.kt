package com.example.achiliveapp.data.api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class RemoteSource<T, I>(protected val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : IFilterable<T, I> {

    abstract suspend fun getAll(): Result<List<T>>
    abstract suspend fun insert(t: T): Result<T>
    abstract suspend fun update(t: T) : Result<T>
    abstract suspend fun delete(t : T) : Result<T>

}