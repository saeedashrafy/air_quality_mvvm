package com.ashr.cleanMvvmAir.domain.repository

import com.ashr.cleanMvvmAir.domain.model.DomainCountry

interface CountryRepository {
    suspend fun getAllCountries(): List<DomainCountry>
}