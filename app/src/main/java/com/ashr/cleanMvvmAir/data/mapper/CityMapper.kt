package com.ashr.cleanMvvmAir.data.mapper

import com.ashr.cleanMvvmAir.data.entity.CityEntity
import com.ashr.cleanMvvmAir.domain.model.DomainCity
import javax.inject.Inject

class CityMapper  @Inject constructor(): Mapper<CityEntity, DomainCity> {
    override fun mapFromEntity(entity: CityEntity): DomainCity {
        return DomainCity(entity.name)
    }

    override fun mapToEntity(domainModel: DomainCity): CityEntity {
        TODO("Not yet implemented")
    }
}