package com.ashr.cleanMvvmAir.domain.usecase

import com.ashr.cleanMvvmAir.domain.repository.CountryRepository
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val countryRepository: CountryRepository
) {
    suspend operator fun invoke() = countryRepository.getAllCountries()
}