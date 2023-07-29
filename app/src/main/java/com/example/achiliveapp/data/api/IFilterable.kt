package com.example.achiliveapp.data.api

interface IFilterable<T, I> {

    suspend fun where(fieldName: String, value: Any): Result<List<T>>
    suspend fun getById(id : I): Result<T>
}