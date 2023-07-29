package com.example.achiliveapp.main.states

open class ListUiState<T>(
    val data: List<T>? = null,
    val message: String? = null,
    val exception: Exception? = null
){

    private val filteredList = data

    class Loading<T>(message: String = ""): ListUiState<T>(message = message)
    class Success<T>(items: List<T>) : ListUiState<T>(data = items)
    class Error<T>(e: Exception): ListUiState<T>(exception = e)

    class ItemChange<T>(val position: Int) : ListUiState<T>()

    class ItemRemoved<T>(val position: Int) : ListUiState<T>()

    fun filter(filter: (String) -> List<T>){

    }

    fun isSuccess(): Boolean{
        return  this is Success<T>
    }

    fun isError(): Boolean{
        return  this is Error<T>
    }

    fun isLoading(): Boolean{
        return  this is Loading<T>
    }

    fun isItemChange() = this is ItemChange<T>
    fun isItemRemoved() = this is ItemRemoved<T>

}