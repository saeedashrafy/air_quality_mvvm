package com.ashr.cleanMvvmAir.remote.mapper

import com.ashr.cleanMvvmAir.data.entity.CountryEntity
import com.ashr.cleanMvvmAir.remote.model.NetworkCountry
import javax.inject.Inject

class CountryMapper @Inject constructor() : EntityMapper<NetworkCountry, CountryEntity> {
    override fun mapFromRemote(type: NetworkCountry): CountryEntity {
        return CountryEntity(name = type.name)
    }
}