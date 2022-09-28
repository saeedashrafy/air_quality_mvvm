package com.ashr.cleanMvvmAir.remote.mapper

import com.ashr.cleanMvvmAir.data.entity.StateEntity
import com.ashr.cleanMvvmAir.remote.model.NetworkState
import javax.inject.Inject

class StateMapper @Inject constructor() : EntityMapper<NetworkState, StateEntity> {
    override fun mapFromRemote(type: NetworkState): StateEntity {
        return StateEntity(name = type.name)
    }
}