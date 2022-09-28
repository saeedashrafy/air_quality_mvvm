package com.ashr.cleanMvvmAir.data.mapper

import com.ashr.cleanMvvmAir.data.entity.CountryEntity
import com.ashr.cleanMvvmAir.domain.model.DomainCountry
import javax.inject.Inject

class CountryMapper @Inject constructor() : Mapper<CountryEntity, DomainCountry> {
    override fun mapFromEntity(entity: CountryEntity): DomainCountry {
        return DomainCountry(name = entity.name)
    }

    override fun mapToEntity(domainModel: DomainCountry): CountryEntity {
        TODO("Not yet implemented")
    }
}