package com.ashr.cleanMvvmAir.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ashr.cleanMvvmAir.R

@Composable
fun ErrorHandler(onRetryClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
        Button(onClick = {
            onRetryClick()
        }) {
            Text(text = "retry", style = MaterialTheme.typography.h4)
            Icon(Icons.Filled.Refresh, contentDescription = "")
        }
    }
}