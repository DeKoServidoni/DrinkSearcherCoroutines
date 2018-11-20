package com.dekoservidoni.drinksearchwithcoroutines.view

import com.dekoservidoni.drinksearchwithcoroutines.data.DrinksNetwork
import com.dekoservidoni.drinksearchwithcoroutines.models.Drink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainRepository @Inject constructor(private val network: DrinksNetwork): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun doSearch(terms: String, success:(List<Drink>) -> Unit, error:(String) -> Unit) = launch {

        try {
            val task = network.searchDrinks(terms)
            val result = task.await()

            when(result.isSuccessful) {
                true -> result.body()?.let { success(it.drinks) }
                false -> error(result.message())
            }

        } catch (ex: Exception) {
            error(ex.localizedMessage)
        }
    }
}