package com.ashr.cleanMvvmAir.data.di

import com.ashr.cleanMvvmAir.data.repository.CityRepositoryImpl
import com.ashr.cleanMvvmAir.data.repository.CountryRepositoryImpl
import com.ashr.cleanMvvmAir.data.repository.StateRepositoryImpl
import com.ashr.cleanMvvmAir.domain.repository.CityRepository
import com.ashr.cleanMvvmAir.domain.repository.CountryRepository
import com.ashr.cleanMvvmAir.domain.repository.StateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindCountryRepository(countryRepositoryImpl: CountryRepositoryImpl):
            CountryRepository

    @Binds
    abstract fun bindStateRepository(stateRepositoryImpl: StateRepositoryImpl): StateRepository

    @Binds
    abstract fun bindCityRepository(cityRepositoryImpl: CityRepositoryImpl): CityRepository
}