package com.open.weather.map.remote

import com.open.weather.map.data.CityWeatherDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapAPI {


    @GET("weather")
    suspend fun getSearchResults(
        @Query("q") str: String, @Query("appid") appid:String):CityWeatherDetails

}