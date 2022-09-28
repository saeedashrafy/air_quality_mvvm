package com.ashr.cleanMvvmAir.presentation.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White


val Teal200 = Color(0xFF03DAC5)
val White200 = Color(0xFFf3f3f3)
val Black200 = Color(0xFF121212)


val BackgroundLight = Color(0xFFF5F2F5)
val BackgroundDark = Color(0xFF24292E)

val DividerLight = Color(0XFFF2F4F5)
val DividerDark = Color(0xFF3F3E3E)

val navigationBackIconDark = White
val navigationBackIconLight = Black

val aqi_good = Color(0XFF48ac66)
val aqi_moderate = Color(0XFFfdcd2f)
val aqi_unhealthyForSensitive = Color(0XFFff5c01)
val aqi_unhealthy = Color(0XFFff2a54)
val aqi_veryUnhealthy = Color(0XFF4b3c99)
val aqi_hazardous = Color(0XFF971917)

val Colors.navigationBackIconColor: Color
    @Composable get() = if (isLight) navigationBackIconLight else navigationBackIconDark

val Colors.dividerColor: Color
    @Composable get() = if (isLight) DividerLight else DividerDark

val Colors.backgroundColor: Color
    @Composable get() = if (isLight) BackgroundLight else BackgroundDark

