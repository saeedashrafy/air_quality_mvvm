package com.ashr.cleanMvvmAir.data.datasource.remote

import com.ashr.cleanMvvmAir.data.entity.CityDataEntity
import com.ashr.cleanMvvmAir.data.entity.CityEntity

interface CityRemoteDataSource {
    suspend fun getCities(countryName: String, stateName: String): List<CityEntity>
    suspend fun getCityData(
        countryName: String,
        stateName: String,
        cityName: String
    ): CityDataEntity
}