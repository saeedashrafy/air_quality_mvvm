package com.ashr.cleanMvvmAir.remote.mapper

interface EntityMapper<in REMOTE, out ENTITY> {

    fun mapFromRemote(type: REMOTE): ENTITY
}