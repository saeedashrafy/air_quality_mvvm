package com.ashr.cleanMvvmAir.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ashr.cleanMvvmAir.R

@Composable
fun ItemRow(title: String, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.ic_country),
        contentDescription = "",
        modifier = Modifier
            .padding(10.dp)
            .width(50.dp)
            .height(50.dp),
        alpha = 0.5f
    )
    Text(
        text = title,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 30.dp, horizontal = 20.dp)
    )
}