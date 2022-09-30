package com.ashr.cleanMvvmAir.presentation.state

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
fun StateScreen(
    countryName: String?,
    title: MutableState<String>,
    onNavigateToCities: (countryName: String, stateName: String) -> Unit
) {
    title.value = "States of $countryName"
    val viewModel: StateViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (uiState.fetchedStates.isEmpty())
            viewModel.processIntents(StateUiIntent.GetStates(countryName ?: ""))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading)
            CircularLoadingView()
        else if (uiState.fetchedStates.isEmpty()) {
            ErrorHandler {
                viewModel.processIntents(
                    StateUiIntent.GetStates(
                        countryName ?: ""
                    )
                )
            }
        } else {
            Text(
                "Select State",
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
                        StateUiIntent.ChangeFilter(
                            value
                        )
                    )
                })
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(uiState.filteredStates) { state ->
                    ItemRow(
                        state.name,
                        onClick = { onNavigateToCities(countryName ?: "", state.name) })
                }
            }
        }
    }
}