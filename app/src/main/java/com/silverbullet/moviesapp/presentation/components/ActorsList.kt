package com.silverbullet.moviesapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.silverbullet.moviesapp.data.remote.TMDBApi
import com.silverbullet.moviesapp.domain.model.Actor
import com.silverbullet.moviesapp.presentation.ui.theme.Montserrat

@Composable
fun ActorsList(
    modifier: Modifier = Modifier,
    actors: List<Actor>,
    onItemClick: (Int) -> Unit = {}
) {
    LazyRow(modifier = modifier) {
        items(actors) { actor ->
            Column(
                modifier = Modifier.clickable { onItemClick(actor.id) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                ) {
                    AsyncImage(
                        model = TMDBApi.buildImageUrl(actor.profilePath, 500),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = actor.name,
                    fontSize = 12.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
        }
    }
}