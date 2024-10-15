package com.open.weather.map.viewModel

import com.open.weather.map.data.CityWeatherDetails

data class OpenWeatherMapState(var cityDetails:CityWeatherDetails? = null, var searchText:String="", var isGotError:Boolean = false, var isLoading:Boolean = false)