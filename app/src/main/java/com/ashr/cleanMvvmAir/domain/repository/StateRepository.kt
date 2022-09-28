package com.ashr.cleanMvvmAir.domain.repository

import com.ashr.cleanMvvmAir.domain.model.DomainState

interface StateRepository {
    suspend fun getStatesOfCountry(countryName: String): List<DomainState>
}