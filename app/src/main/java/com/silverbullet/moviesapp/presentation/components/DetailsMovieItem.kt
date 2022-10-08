package com.silverbullet.moviesapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.silverbullet.moviesapp.R
import com.silverbullet.moviesapp.data.remote.TMDBApi
import com.silverbullet.moviesapp.domain.model.MovieDetails
import com.silverbullet.moviesapp.presentation.ui.theme.Montserrat
import com.silverbullet.moviesapp.presentation.ui.theme.OrangeColor
import com.silverbullet.moviesapp.presentation.ui.theme.SoftColor

@Composable
fun DetailsMovieItem(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails,
    imageWidth: Dp = 112.dp,
    imageHeight: Dp = 147.dp
) {
    Row(modifier = modifier) {
        Box {
            AsyncImage(
                model = TMDBApi.buildImageUrl(movieDetails.posterPath, 400),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(imageWidth)
                    .height(imageHeight)
                    .clip(RoundedCornerShape(12.dp))
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(SoftColor)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = movieDetails.voteAverage.toString(),
                            color = OrangeColor,
                            fontSize = 12.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.height(imageHeight*0.5f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movieDetails.title,
                style = MaterialTheme.typography.h4,
                color = Color.White,
                overflow = TextOverflow.Ellipsis
            )
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = movieDetails.releaseDate, color = Color.Gray, fontSize = 12.sp)
            }
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "33 minutes", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}