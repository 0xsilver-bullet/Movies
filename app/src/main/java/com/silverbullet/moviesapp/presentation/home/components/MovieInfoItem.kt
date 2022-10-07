package com.silverbullet.moviesapp.presentation.home.components

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.silverbullet.moviesapp.data.remote.TMDBApi
import com.silverbullet.moviesapp.domain.model.MovieInfo
import com.silverbullet.moviesapp.R
import com.silverbullet.moviesapp.presentation.ui.theme.Montserrat
import com.silverbullet.moviesapp.presentation.ui.theme.OrangeColor

@Composable
fun MovieInfoItem(
    modifier: Modifier = Modifier,
    movieInfo: MovieInfo,
    backgroundColor: Color
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
    ) {
        AsyncImage(
            model = TMDBApi.buildImageUrl(movieInfo.posterPath, 300),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
        )
        Box(
            modifier = Modifier
                .offset((-8).dp,8.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0x6B252836))
        )
        {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.ic_star), contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = movieInfo.voteAverage.toString(),
                    color = OrangeColor,
                    fontSize = 12.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = movieInfo.title,
                style = MaterialTheme.typography.h5,
                color = Color.White,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}