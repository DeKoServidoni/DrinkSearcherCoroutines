package com.dekoservidoni.drinksearchwithcoroutines.models.status

sealed class SearchStatus<out T> {
    data class Success<out T>(val content: T? = null): SearchStatus<T>()
    data class Error(val errorCode: Int? = 0, val error: String? = null): SearchStatus<Nothing>()
}