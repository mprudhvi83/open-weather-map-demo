package com.open.weather.map.repo

import com.open.weather.map.data.CityWeatherDetails
import com.open.weather.map.remote.OpenWeatherMapAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.http.Query
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenWeatherMapRepositoryImpl @Inject constructor(val api: OpenWeatherMapAPI,):OpenWeatherMapRepository {
    private val API_ID: String = "52255dd88ca9182891f84328bcff6a53"
    override suspend fun getSearchResults(query: String): Flow<Result<CityWeatherDetails>> {
        return flow<Result<CityWeatherDetails>> {
            try {
                emit(Result.Loading(isLoading = true))
//                delay(2000)
                emit(Result.Success(api.getSearchResults(query, API_ID)))
                emit(Result.Loading(isLoading = false))
            } catch (e:IOException) {
                emit(Result.Error(message = "Got IO Exception"))
            } catch (e:HttpException) {
                emit(Result.Error(message = "Got HTTP Exception"))
            } catch (e:Exception) {
                emit(Result.Error(message = "Got Un know Exception"))
            }
        }.flowOn(Dispatchers.IO)
    }
}