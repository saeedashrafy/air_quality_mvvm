package com.ashr.cleanMvvmAir.data.repository

import com.ashr.cleanMvvmAir.data.datasource.remote.StateRemoteDataSource
import com.ashr.cleanMvvmAir.data.mapper.StateMapper
import com.ashr.cleanMvvmAir.domain.model.DomainState
import com.ashr.cleanMvvmAir.domain.repository.StateRepository
import javax.inject.Inject

class StateRepositoryImpl @Inject constructor(
    private val stateRemoteDataSource: StateRemoteDataSource,
    private val mapper: StateMapper
) :
    StateRepository {
    override suspend fun getStatesOfCountry(countryName: String): List<DomainState> {
        return stateRemoteDataSource.getStates(countryName)
            .map { mapper.mapFromEntity(it) }
    }
}