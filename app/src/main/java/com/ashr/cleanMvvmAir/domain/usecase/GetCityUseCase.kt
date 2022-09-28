package com.ashr.cleanMvvmAir.domain.usecase

import com.ashr.cleanMvvmAir.domain.repository.CityRepository
import javax.inject.Inject

class GetCityUseCase @Inject constructor(private val cityRepository: CityRepository) {
    suspend operator fun invoke(countryName: String, stateName: String) =
        cityRepository.getCities(countryName, stateName)
}