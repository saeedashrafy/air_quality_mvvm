package com.ashr.cleanMvvmAir.data.repository

import com.ashr.cleanMvvmAir.data.datasource.remote.CityRemoteDataSource
import com.ashr.cleanMvvmAir.data.entity.toDomain
import com.ashr.cleanMvvmAir.data.mapper.CityMapper
import com.ashr.cleanMvvmAir.domain.model.DomainCity
import com.ashr.cleanMvvmAir.domain.model.DomainCityData
import com.ashr.cleanMvvmAir.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val remoteCityRemoteDataSource: CityRemoteDataSource,
    private val mapper: CityMapper
) :
    CityRepository {
    override suspend fun getCities(countryName: String, stateName: String): List<DomainCity> {
        return remoteCityRemoteDataSource.getCities(countryName, stateName)
            .map { mapper.mapFromEntity(it) }
    }

    override suspend fun getCityData(
        countryName: String,
        stateName: String,
        cityName: String
    ): DomainCityData {
        return remoteCityRemoteDataSource.getCityData(countryName, stateName, cityName).toDomain()
    }
}