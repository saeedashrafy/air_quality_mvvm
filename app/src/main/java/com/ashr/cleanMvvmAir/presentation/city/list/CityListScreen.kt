package com.ashr.cleanMvvmAir.presentation.city.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashr.cleanMvvmAir.presentation.ErrorHandler
import com.ashr.cleanMvvmAir.presentation.ItemRow
import com.ashr.cleanMvvmAir.presentation.ui.theme.GradientFirst
import com.ashr.cleanMvvmAir.presentation.ui.theme.GradientSecond
import com.ashr.cleanMvvmAir.presentation.ui.theme.GradientThird
import com.ashr.cleanMvvmAir.presentation.ui.widget.AppTextField
import com.ashr.cleanMvvmAir.presentation.ui.widget.CircularLoadingView

@Composable
fun CityListScreen(
    countryName: String?,
    stateName: String?,
    title: MutableState<String>,
    onNavigateToDetail: (countryName: String, stateName: String, cityName: String) -> Unit
) {
    title.value = "Cities of $stateName, $countryName"
    val viewModel: CityViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.processIntents(CityUiIntent.GetCities(countryName ?: "", stateName ?: ""))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading)
            CircularLoadingView()
        else if (uiState.fetchedCities.isEmpty()) {
            ErrorHandler {
                viewModel.processIntents(CityUiIntent.GetCities(countryName ?: "", stateName ?: ""))
            }
        } else {
            Text(
                "Select City",
                style = MaterialTheme.typography.h4.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                GradientFirst,
                                GradientSecond,
                                GradientThird,
                            )
                        )
                    )
                    .padding(10.dp)
            )
            AppTextField(
                value = uiState.filter,
                label = "filter",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                onValueChange = { value ->
                    viewModel.processIntents(
                        CityUiIntent.ChangeFilter(
                            value
                        )
                    )
                })
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(uiState.filteredCities) { city ->

                    ItemRow(
                        city.name,
                        onClick = {
                            onNavigateToDetail(
                                countryName ?: "",
                                stateName ?: "",
                                city.name
                            )
                        })


                }
            }
        }
    }
}