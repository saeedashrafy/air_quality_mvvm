package com.ashr.cleanMvvmAir.presentation.state

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashr.cleanMvvmAir.presentation.ErrorHandler
import com.ashr.cleanMvvmAir.presentation.ItemRow
import com.ashr.cleanMvvmAir.presentation.ui.theme.dividerColor
import com.ashr.cleanMvvmAir.presentation.ui.widget.AppOutlinedTextField
import com.ashr.cleanMvvmAir.presentation.ui.widget.CircularLoadingView
import timber.log.Timber

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
            .fillMaxWidth()
            .padding(10.dp),
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
            AppOutlinedTextField(
                value = uiState.filter,
                label = "write yout State",
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
                            state.name,
                            onClick = { onNavigateToCities(countryName ?: "", state.name) })
                    }

                }
            }
        }
    }
}