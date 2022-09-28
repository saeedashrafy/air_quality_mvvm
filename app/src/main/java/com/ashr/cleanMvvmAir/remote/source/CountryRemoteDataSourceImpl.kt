package com.ashr.cleanMvvmAir.remote.source

import com.ashr.cleanMvvmAir.data.datasource.remote.CountryRemoteDataSource
import com.ashr.cleanMvvmAir.data.entity.CountryEntity
import com.ashr.cleanMvvmAir.remote.mapper.CountryMapper
import com.ashr.cleanMvvmAir.remote.service.AirQualityService
import javax.inject.Inject

class CountryRemoteDataSourceImpl @Inject constructor(
    private val airQualityService: AirQualityService,
    private val countryMapper: CountryMapper
) : CountryRemoteDataSource {
    override suspend fun getAllCountries(): List<CountryEntity> {
        return airQualityService.getAllCountries().data?.map { countryMapper.mapFromRemote(it) }
            ?: emptyList()
    }
}