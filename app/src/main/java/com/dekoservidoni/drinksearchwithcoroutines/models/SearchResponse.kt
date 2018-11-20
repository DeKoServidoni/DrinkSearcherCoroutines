package com.dekoservidoni.drinksearchwithcoroutines.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("drinks")
    var drinks: List<Drink> = ArrayList()
)