package com.ashr.cleanMvvmAir.presentation.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White


val Teal200 = Color(0xFF03DAC5)
val White200 = Color(0xFFf3f3f3)
val Black200 = Color(0xFF121212)


val BackgroundLight = Color(0xFFFFFFFF)
val BackgroundDark = Color(0xFF121212)

val DividerLight = Color(0xFFEBEBEB)
val DividerDark = Color(0xFF3F3E3E)


val GrayLight = Color(0XFFf2f4f5)
val GrayDark = Color(0XFF3f3e3e)

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

