package com.ashr.cleanMvvmAir.presentation.city.data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ashr.cleanMvvmAir.domain.model.AqiLevel
import com.ashr.cleanMvvmAir.presentation.ErrorHandler
import com.ashr.cleanMvvmAir.presentation.ui.theme.*
import com.ashr.cleanMvvmAir.presentation.ui.widget.CircularLoadingView

@Composable
fun CityDataScreen(
    countryName: String?,
    stateName: String?,
    cityName: String?,
    title: MutableState<String>
) {
    title.value = "AQI of $cityName from $countryName"
    val viewModel: CityDataViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.processIntents(
            CityDataUiIntent.GetData(
                countryName ?: "",
                stateName ?: "",
                cityName ?: ""
            )
        )
    }
    if (uiState.isLoading) {
        CircularLoadingView()
    } else if (uiState.error.isNotEmpty()) {
        ErrorHandler {
            viewModel.processIntents(
                CityDataUiIntent.GetData(
                    countryName ?: "",
                    stateName ?: "",
                    cityName ?: ""
                )
            )
        }
    } else
        uiState.data?.let { cityData ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            cityData.domainPollutionData.aqiLevel.getAppropriateColor(),
                            RoundedCornerShape(10.dp)
                        )
                        .padding(15.dp)
                ) {
                    Column {
                        Text(
                            text = "US AQI ${cityData.domainPollutionData.airQualityIndexUs}",
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier
                                .padding(5.dp)
                                .background(
                                    Color.White.copy(alpha = 0.5f), RoundedCornerShape(7.dp)
                                )
                                .padding(5.dp)

                        )
                        Text(
                            text = cityData.domainPollutionData.aqiLevel.levelName,
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier
                                .padding(5.dp)

                        )
                    }
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(cityData.domainPollutionData.aqiLevel.getAppropriateIcon())
                            .crossfade(true)
                            .build(),
                        contentDescription = cityData.domainPollutionData.aqiLevel.levelName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(70.dp)
                            .height(70.dp)
                            .background(Color.White.copy(alpha = 0.5f), CircleShape)
                            .padding(3.dp)

                    )
                }
                SpecificRow("Temperature", "${cityData.domainWeatherData.temperature}°C")
                SpecificRow("Humidity", "${cityData.domainWeatherData.humidity}%")
                SpecificRow("Pressure", "${cityData.domainWeatherData.pressure} mb")
            }


        }

}

fun AqiLevel.getAppropriateColor(): Color {
    return when (this) {
        AqiLevel.Good -> aqi_good
        AqiLevel.Moderate -> aqi_moderate
        AqiLevel.UnhealthyForSensitiveGroups -> aqi_unhealthyForSensitive
        AqiLevel.Unhealthy -> aqi_unhealthy
        AqiLevel.VeryUnhealthy -> aqi_veryUnhealthy
        AqiLevel.Hazardous -> aqi_hazardous
        AqiLevel.Unknown -> aqi_good
    }
}

fun AqiLevel.getAppropriateIcon(): String {
    return when (this) {
        AqiLevel.Good -> "https://img.icons8.com/emoji/48/000000/smiling-face-with-smiling-eyes.png"
        AqiLevel.Moderate -> "https://img.icons8.com/emoji/48/000000/slightly-smiling-face.png"
        AqiLevel.UnhealthyForSensitiveGroups -> "https://img.icons8.com/emoji/48/000000/pensive-face.png"
        AqiLevel.Unhealthy -> "https://img.icons8.com/emoji/48/000000/sad-but-relieved-face.png"
        AqiLevel.VeryUnhealthy -> "https://img.icons8.com/emoji/48/000000/face-with-medical-mask.png"
        AqiLevel.Hazardous -> "https://img.icons8.com/emoji/48/000000/face-vomiting.png"
        AqiLevel.Unknown -> "https://img.icons8.com/emoji/48/000000/smiling-face-with-smiling-eyes.png"
    }
}