package com.open.weather.map.di

import com.open.weather.map.remote.OpenWeatherMapAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationOpenWeatherMapModule {
    @Provides
    @Singleton
    fun getSchoolApi(): OpenWeatherMapAPI {
        val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit.create(OpenWeatherMapAPI::class.java)
    }
}