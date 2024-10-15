package com.open.weather.map.viewModel

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.open.weather.map.remote.OpenWeatherMapAPI
import com.open.weather.map.repo.OpenWeatherMapPreference
import com.open.weather.map.repo.OpenWeatherMapRepository
import com.open.weather.map.di.ApplicationOpenWeatherMapModule
import com.open.weather.map.repo.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenWeatherMapViewModel @Inject constructor(private  var repository: OpenWeatherMapRepository, private  var preference:OpenWeatherMapPreference): ViewModel() {
    @SuppressLint("StaticFieldLeak")
    var context: Context? = null
    var stateObject by mutableStateOf(OpenWeatherMapState())
//    private val API_ID: String = "52255dd88ca9182891f84328bcff6a53"

    val api: OpenWeatherMapAPI = ApplicationOpenWeatherMapModule.getSchoolApi()

    fun restoreFromLocal(ctx: Context, fallbackSearch: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val lastSearch: String = preference.getRecentSearch()
            if (lastSearch.isNotBlank()) {
                getResult(lastSearch)
                stateObject = stateObject.copy(searchText = lastSearch)
            } else {
                fallbackSearch?.let {
                    if (it.isNotBlank()) {
                        getResult(it)
                    }
                    stateObject = stateObject.copy(searchText = fallbackSearch)
                }
            }
        }
    }

    private fun getResult(searchText: String) {
        println("searchText = $searchText")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getSearchResults(searchText).collect { result ->
                    when (result) {
                        is Result.Error -> {
                            stateObject = stateObject.copy(
                                cityDetails = null,
                                isGotError = true,
                                isLoading = false
                            )
                        }

                        is Result.Loading -> {
                            stateObject = stateObject.copy(isLoading = result.isLoading, isGotError = false)
                        }

                        is Result.Success -> {
                            stateObject = stateObject.copy(cityDetails = result.data, isGotError = false)
                            preference.saveRecentSearch(searchText)
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }




    fun updateSearchText(newText:String) {
        stateObject = stateObject.copy(searchText = newText)
    }

    fun getSeachResults() {
        viewModelScope.launch(Dispatchers.IO) {
            getResult(stateObject.searchText)
        }
    }

}