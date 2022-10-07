package com.silverbullet.moviesapp.presentation.movie_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StoryLineSection(
    modifier: Modifier = Modifier,
    story: String,
    textColor: Color = Color.White
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Story Line",
            style = MaterialTheme.typography.h4,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = story,
            style = MaterialTheme.typography.h5,
            color = textColor
        )
    }
}