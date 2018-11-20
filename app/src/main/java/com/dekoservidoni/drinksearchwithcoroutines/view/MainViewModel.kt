package com.dekoservidoni.drinksearchwithcoroutines.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import android.view.View
import com.dekoservidoni.drinksearchwithcoroutines.BR
import com.dekoservidoni.drinksearchwithcoroutines.BaseViewModel
import com.dekoservidoni.drinksearchwithcoroutines.data.DrinksNetwork
import com.dekoservidoni.drinksearchwithcoroutines.models.Drink
import com.dekoservidoni.drinksearchwithcoroutines.models.status.SearchStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainViewModel @Inject constructor(private val network: DrinksNetwork): BaseViewModel(), CoroutineScope {

    private var contentVisibility = View.GONE
    private var progressVisibility = View.GONE

    private var job: Job? = null
    private val _data = MutableLiveData<SearchStatus<List<Drink>>>()

    val data: LiveData<SearchStatus<List<Drink>>>
        get() = _data

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun search(terms: String) {
        showLoading(true)
        job = doSearch(terms)
    }

    /// Bindable methods

    @Bindable
    fun getContentVisibility(): Int = contentVisibility

    @Bindable
    fun getProgressVisibility(): Int = progressVisibility

    /// Private methods

    private fun doSearch(terms: String) = launch {

        try {
            val task = network.searchDrinks(terms)
            val result = task.await()

            showLoading(false)

            when(result.isSuccessful) {
                true -> result.body()?.let { _data.postValue(SearchStatus.Success(it.drinks)) }
                false -> _data.postValue(SearchStatus.Error(errorCode = 0, error = result.message()))
            }

        } catch (ex: Exception) {
            _data.postValue(SearchStatus.Error(errorCode = 0, error = ex.localizedMessage))
        }
    }

    private fun showLoading(show: Boolean) {
        contentVisibility = if(show) View.GONE else View.VISIBLE
        progressVisibility = if(show) View.VISIBLE else View.GONE

        notifyPropertyChanged(BR.contentVisibility)
        notifyPropertyChanged(BR.progressVisibility)
    }
}