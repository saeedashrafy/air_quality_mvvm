package com.ashr.cleanMvvmAir.presentation.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Toolbar(
    navigationIcon: @Composable() (() -> Unit)?,
    title: String,
    onSwitchTheme: () -> Unit,
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.h4,
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = navigationIcon ?: {},
        actions = {
            IconButton(onClick = { onSwitchTheme() }) {
                Icon(Icons.Filled.Face, "")
            }

        }
    )
}