package com.ashr.cleanMvvmAir.remote.source

import com.ashr.cleanMvvmAir.data.datasource.remote.StateRemoteDataSource
import com.ashr.cleanMvvmAir.data.entity.StateEntity
import com.ashr.cleanMvvmAir.remote.mapper.StateMapper
import com.ashr.cleanMvvmAir.remote.service.AirQualityService
import javax.inject.Inject

class StateRemoteDataSourceImpl @Inject constructor(
    private val airQualityService: AirQualityService,
    private val stateMapper: StateMapper
) : StateRemoteDataSource {
    override suspend fun getStates(countryName: String): List<StateEntity> {
        return airQualityService.getStates(countryName).data?.map { stateMapper.mapFromRemote(it) }
            ?: emptyList()
    }
}