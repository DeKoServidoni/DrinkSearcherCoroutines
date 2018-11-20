package com.dekoservidoni.drinksearchwithcoroutines.view

import android.arch.lifecycle.ViewModel
import com.dekoservidoni.drinksearchwithcoroutines.models.Drink
import java.util.*

class MainRowViewModel(var drink: Drink) : ViewModel() {

    fun getRowDescription(): String {
        return drink.iba?.let { "%s\n%s".format(Locale.getDefault(), drink.name, drink.iba) }
            ?: run { "%s".format(Locale.getDefault(), drink.name) }
    }
}