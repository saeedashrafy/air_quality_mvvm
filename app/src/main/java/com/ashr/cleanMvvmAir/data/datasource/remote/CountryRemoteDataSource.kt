package com.ashr.cleanMvvmAir.data.datasource.remote

import com.ashr.cleanMvvmAir.data.entity.CountryEntity

interface CountryRemoteDataSource  {
   suspend fun getAllCountries():List<CountryEntity>
}