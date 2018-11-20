package com.dekoservidoni.drinksearchwithcoroutines.data

import com.dekoservidoni.drinksearchwithcoroutines.models.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinksNetwork {

    @GET("search.php")
    fun searchDrinks(@Query("s") term: String): Deferred<Response<SearchResponse>>
}