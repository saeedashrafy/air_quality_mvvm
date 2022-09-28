package com.ashr.cleanMvvmAir.presentation.city.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashr.cleanMvvmAir.R
import com.ashr.cleanMvvmAir.presentation.ItemRow
import com.ashr.cleanMvvmAir.presentation.ui.theme.dividerColor
import com.ashr.cleanMvvmAir.presentation.ui.widget.AppOutlinedTextField
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
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading)
            CircularLoadingView()
        else if (uiState.fetchedCities.isEmpty()) {

            Image(
                painter = painterResource(id = R.drawable.ic_empty),
                modifier = Modifier
                    .padding(top = 40.dp)
                    .size(250.dp),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.no_data),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )

        } else {
            AppOutlinedTextField(
                value = uiState.filter,
                label = "Write your city",
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .border(
                                width = 1.25.dp,
                                color = MaterialTheme.colors.dividerColor,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
}