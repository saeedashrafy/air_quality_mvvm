package com.ashr.cleanMvvmAir.presentation.ui.widget

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ashr.cleanMvvmAir.presentation.ui.theme.dividerColor


@Composable
fun AppOutlinedTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        label = {
            Text(label, style = MaterialTheme.typography.h5)
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colors.onPrimary,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.onPrimary,
            cursorColor = MaterialTheme.colors.onPrimary,
        )
    )
}

@Composable
fun AppTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value, onValueChange = { onValueChange(it) },
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        label = {
            Text(label, style = MaterialTheme.typography.h5)
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colors.onPrimary,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.onPrimary,
            cursorColor = MaterialTheme.colors.onPrimary,
            backgroundColor = MaterialTheme.colors.onSurface,
            unfocusedLabelColor = Color.Gray

        )
    )
}