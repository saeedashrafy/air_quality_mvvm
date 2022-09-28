package com.ashr.cleanMvvmAir.presentation

sealed class Screen(val route: String) {
    object Country : Screen(route = "country")
    object State : Screen(route = "state/{countryName}")
    object City : Screen(route = "city/{countryName}/{stateName}")
    object CityDetail : Screen(route = "cityDetail/{countryName}/{stateName}/{cityName}")
}
