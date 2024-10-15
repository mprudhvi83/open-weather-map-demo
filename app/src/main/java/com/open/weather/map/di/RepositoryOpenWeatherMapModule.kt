package com.open.weather.map.di

import com.open.weather.map.repo.OpenWeatherMapRepository
import com.open.weather.map.repo.OpenWeatherMapRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryOpenWeatherMapModule {

    @Binds
    @Singleton
    abstract fun bindSchoolRepository(
        stockRepositoryImpl: OpenWeatherMapRepositoryImpl
    ): OpenWeatherMapRepository
}