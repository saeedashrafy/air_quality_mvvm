package com.ashr.cleanMvvmAir.remote.service

import com.ashr.cleanMvvmAir.remote.model.CityQualityResponse
import com.ashr.cleanMvvmAir.remote.model.CityResponse
import com.ashr.cleanMvvmAir.remote.model.CountryResponse
import com.ashr.cleanMvvmAir.remote.model.StateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityService {

    @GET("countries")
    suspend fun getAllCountries(): CountryResponse

    @GET("states")
    suspend fun getStates(@Query("country") countryName: String): StateResponse

    @GET("cities")
    suspend fun getCities(
        @Query("country") countryName: String,
        @Query("state") stateName: String
    ): CityResponse

    @GET("city")
    suspend fun getCityData(
        @Query("country") countryName: String,
        @Query("state") stateName: String,
        @Query("city") cityName: String,
    ): CityQualityResponse
}