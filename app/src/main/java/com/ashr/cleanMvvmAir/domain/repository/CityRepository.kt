package com.ashr.cleanMvvmAir.domain.repository

import com.ashr.cleanMvvmAir.domain.model.DomainCity
import com.ashr.cleanMvvmAir.domain.model.DomainCityData

interface CityRepository {
    suspend fun getCities(countryName: String, stateName: String): List<DomainCity>
    suspend fun getCityData(
        countryName: String,
        stateName: String,
        cityName: String
    ): DomainCityData
}