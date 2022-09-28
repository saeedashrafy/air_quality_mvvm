package com.ashr.cleanMvvmAir.domain.usecase

import com.ashr.cleanMvvmAir.domain.repository.CityRepository
import javax.inject.Inject

class GetCityDataUseCase @Inject constructor(private val cityRepository: CityRepository) {
    suspend operator fun invoke(
        countryName: String,
        stateName: String,
        cityName: String
    ) = cityRepository.getCityData(countryName, stateName, cityName)
}