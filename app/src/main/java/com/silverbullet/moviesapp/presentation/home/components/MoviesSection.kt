package com.silverbullet.moviesapp.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silverbullet.moviesapp.domain.model.MovieInfo
import com.silverbullet.moviesapp.presentation.ui.theme.BlueAccent
import com.silverbullet.moviesapp.presentation.ui.theme.Montserrat
import com.silverbullet.moviesapp.presentation.ui.theme.SoftColor

@Composable
fun MoviesSection(
    modifier: Modifier = Modifier,
    sectionHeader: String,
    moviesList: List<MovieInfo>,
    actionString: String,
    onActionStringClicked: () -> Unit = {},
    onScrolledToEnd: () -> Unit = {},
    onItemClick: (Int,String) -> Unit = {_,_ ->}
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = sectionHeader,
                style = MaterialTheme.typography.h4,
                color = Color.White
            )
            Text(
                text = actionString,
                color = BlueAccent,
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onActionStringClicked() }
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(modifier = Modifier.padding(start = 24.dp)) {
            itemsIndexed(moviesList) { index, movieInfo ->
                if (index == moviesList.lastIndex) {
                    onScrolledToEnd()
                }
                MovieInfoItem(
                    movieInfo = movieInfo,
                    backgroundColor = SoftColor,
                    modifier = Modifier
                        .width(135.dp)
                        .height(230.dp)
                        .clickable { onItemClick(movieInfo.id,movieInfo.title) }
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}
