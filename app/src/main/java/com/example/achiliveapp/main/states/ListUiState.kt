package com.example.achiliveapp.main.states

import com.example.achiliveapp.firebase.AwardSchemeDTO

open class ListUiState<T>(
    val data: List<T>? = null,
    val message: String? = null,
    val exception: Exception? = null
){


    class Loading<T>(message: String = ""): ListUiState<T>(message = message)
    class Success<T>(items: List<T>) : ListUiState<T>(data = items)
    class Error<T>(e: Exception): ListUiState<T>(exception = e)


    fun isSuccess(): Boolean{
        return  this is Success<T>
    }

    fun isError(): Boolean{
        return  this is Error<T>
    }

    fun isLoading(): Boolean{
        return  this is Loading<T>
    }

}