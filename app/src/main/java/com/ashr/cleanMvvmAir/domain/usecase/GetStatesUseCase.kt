package com.ashr.cleanMvvmAir.domain.usecase

import com.ashr.cleanMvvmAir.domain.repository.StateRepository
import javax.inject.Inject

class GetStatesUseCase @Inject constructor(private val stateRepository: StateRepository) {
    suspend operator fun invoke(countryName:String) = stateRepository.getStatesOfCountry(countryName)
}