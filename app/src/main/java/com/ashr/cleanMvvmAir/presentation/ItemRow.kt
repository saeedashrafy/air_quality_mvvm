package com.ashr.cleanMvvmAir.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ashr.cleanMvvmAir.R
import com.ashr.cleanMvvmAir.presentation.ui.theme.dividerColor

@Composable
fun ItemRow(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 15.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.dividerColor,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_country),
            contentDescription = "",
            modifier = Modifier
                .padding(10.dp)
                .width(45.dp)
                .height(45.dp),
        )
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp, horizontal = 20.dp)
        )
    }
}