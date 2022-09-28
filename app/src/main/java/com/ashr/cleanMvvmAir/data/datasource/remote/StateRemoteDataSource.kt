package com.ashr.cleanMvvmAir.data.datasource.remote

import com.ashr.cleanMvvmAir.data.entity.StateEntity

interface StateRemoteDataSource {
    suspend fun getStates(countryName: String): List<StateEntity>
}