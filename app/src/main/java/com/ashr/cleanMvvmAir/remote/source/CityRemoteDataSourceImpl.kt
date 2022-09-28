package com.ashr.cleanMvvmAir.remote.source

import com.ashr.cleanMvvmAir.data.datasource.remote.CityRemoteDataSource
import com.ashr.cleanMvvmAir.data.entity.CityDataEntity
import com.ashr.cleanMvvmAir.data.entity.CityEntity
import com.ashr.cleanMvvmAir.data.entity.PollutionEntity
import com.ashr.cleanMvvmAir.data.entity.WeatherEntity
import com.ashr.cleanMvvmAir.remote.mapper.CityMapper
import com.ashr.cleanMvvmAir.remote.model.toEntity
import com.ashr.cleanMvvmAir.remote.service.AirQualityService
import javax.inject.Inject

class CityRemoteDataSourceImpl @Inject constructor(
    private val airQualityService: AirQualityService,
    private val mapper: CityMapper,
) : CityRemoteDataSource {
    override suspend fun getCities(countryName: String, stateName: String): List<CityEntity> {
        return airQualityService.getCities(countryName, stateName).data?.map {
            mapper.mapFromRemote(
                it
            )
        } ?: emptyList()
    }

    override suspend fun getCityData(
        countryName: String,
        stateName: String,
        cityName: String
    ): CityDataEntity {
        return airQualityService.getCityData(
            countryName,
            stateName,
            cityName
        ).data?.currentData?.toEntity() ?: CityDataEntity(PollutionEntity(), WeatherEntity())
    }
}