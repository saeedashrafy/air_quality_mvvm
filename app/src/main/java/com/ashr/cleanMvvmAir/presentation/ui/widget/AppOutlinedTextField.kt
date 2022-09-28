package com.ashr.cleanMvvmAir.presentation.ui.widget

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
            cursorColor = MaterialTheme.colors.onPrimary

        )
    )
}