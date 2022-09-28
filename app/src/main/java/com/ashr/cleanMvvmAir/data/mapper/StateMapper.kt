package com.ashr.cleanMvvmAir.data.mapper

import com.ashr.cleanMvvmAir.data.entity.StateEntity
import com.ashr.cleanMvvmAir.domain.model.DomainState
import javax.inject.Inject

class StateMapper @Inject constructor() : Mapper<StateEntity, DomainState> {
    override fun mapFromEntity(entity: StateEntity): DomainState {
        return DomainState(name = entity.name)
    }

    override fun mapToEntity(domainModel: DomainState): StateEntity {
        TODO("Not yet implemented")
    }
}