package com.open.weather.map.repo

import com.open.weather.map.data.CityWeatherDetails
import kotlinx.coroutines.flow.Flow

interface OpenWeatherMapRepository {
    suspend fun getSearchResults(query: String): Flow<Result<CityWeatherDetails>>
}