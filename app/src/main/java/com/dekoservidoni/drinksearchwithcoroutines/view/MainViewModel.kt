package com.dekoservidoni.drinksearchwithcoroutines.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import android.view.View
import com.dekoservidoni.drinksearchwithcoroutines.BR
import com.dekoservidoni.drinksearchwithcoroutines.BaseViewModel
import com.dekoservidoni.drinksearchwithcoroutines.models.Drink
import com.dekoservidoni.drinksearchwithcoroutines.models.status.SearchStatus
import kotlinx.coroutines.Job
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {

    private var contentVisibility = View.GONE
    private var progressVisibility = View.GONE

    private var job: Job? = null
    private val _data = MutableLiveData<SearchStatus<List<Drink>>>()

    val data: LiveData<SearchStatus<List<Drink>>>
        get() = _data

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun search(terms: String) {
        showLoading(true)
        job = repository.doSearch(terms,
            success = {
                _data.postValue(SearchStatus.Success(it))
                showLoading(false)
            },
            error = {
                _data.postValue(SearchStatus.Error(errorCode = 0, error = it))
                showLoading(false)
            })
    }

    /// Bindable methods

    @Bindable
    fun getContentVisibility(): Int = contentVisibility

    @Bindable
    fun getProgressVisibility(): Int = progressVisibility

    /// Private methods


    private fun showLoading(show: Boolean) {
        contentVisibility = if (show) View.GONE else View.VISIBLE
        progressVisibility = if (show) View.VISIBLE else View.GONE

        notifyPropertyChanged(BR.contentVisibility)
        notifyPropertyChanged(BR.progressVisibility)
    }
}