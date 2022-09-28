package com.ashr.cleanMvvmAir.domain.model

enum class AqiLevel(val levelName: String) {
    Good("Good"),
    Moderate("Moderate"),
    UnhealthyForSensitiveGroups("Unhealthy For Sensitive Groups"),
    Unhealthy("Unhealthy"),
    VeryUnhealthy("Very Unhealthy"),
    Hazardous("Hazardous"),
    Unknown("Unknown")
}