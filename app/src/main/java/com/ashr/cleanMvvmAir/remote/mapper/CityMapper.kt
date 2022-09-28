package com.ashr.cleanMvvmAir.remote.mapper

import com.ashr.cleanMvvmAir.data.entity.CityEntity
import com.ashr.cleanMvvmAir.remote.model.NetworkCity
import javax.inject.Inject

class CityMapper @Inject constructor(): EntityMapper<NetworkCity, CityEntity> {
    override fun mapFromRemote(type: NetworkCity): CityEntity {
        return CityEntity(type.name)
    }
}