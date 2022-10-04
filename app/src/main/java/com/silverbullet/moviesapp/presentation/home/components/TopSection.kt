package com.silverbullet.moviesapp.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silverbullet.moviesapp.presentation.ui.theme.Montserrat

@Composable
fun TopSection(modifier: Modifier = Modifier) {
    Column(modifier.padding(16.dp)) {
        Text(
            text = "Hello",
            style = MaterialTheme.typography.h4,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Let's browse your favorite movies",
            fontSize = 12.sp,
            color = Color.Gray,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium
        )
    }
}