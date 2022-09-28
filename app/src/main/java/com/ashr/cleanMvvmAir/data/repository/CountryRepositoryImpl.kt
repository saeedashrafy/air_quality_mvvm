package com.ashr.cleanMvvmAir.data.repository

import com.ashr.cleanMvvmAir.data.datasource.remote.CountryRemoteDataSource
import com.ashr.cleanMvvmAir.data.mapper.CountryMapper
import com.ashr.cleanMvvmAir.domain.model.DomainCountry
import com.ashr.cleanMvvmAir.domain.repository.CountryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepositoryImpl @Inject constructor(
    private val remoteDataSource: CountryRemoteDataSource,
    private val countryMapper: CountryMapper
) : CountryRepository {

    override suspend fun getAllCountries(): List<DomainCountry> {
        return remoteDataSource.getAllCountries().map { countryMapper.mapFromEntity(it) }
    }
}