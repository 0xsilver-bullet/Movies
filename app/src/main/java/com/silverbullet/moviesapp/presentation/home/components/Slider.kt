package com.silverbullet.moviesapp.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.silverbullet.moviesapp.data.remote.TMDBApi
import com.silverbullet.moviesapp.domain.model.MovieInfo
import com.silverbullet.moviesapp.presentation.ui.theme.Montserrat
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@Composable
fun Slider(
    modifier: Modifier = Modifier,
    items: List<MovieInfo>,
    sliderHeight: Dp = 200.dp,
    itemWidth: Dp = 300.dp,
    itemHeight: Dp = 150.dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BoxWithConstraints(modifier.height(sliderHeight)) {
            HorizontalPager(
                count = items.size,
                modifier = Modifier.matchParentSize(),
                contentPadding = PaddingValues(horizontal = 40.dp)
            ) { page ->
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                            lerp(
                                start = ScaleFactor(0.85f, 0.85f),
                                stop = ScaleFactor(1f, 1f),
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale.scaleX
                                scaleY = scale.scaleY
                            }
                            alpha = lerp(
                                start = ScaleFactor(0.85f, 0.85f),
                                stop = ScaleFactor(1f, 1f),
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).scaleX
                        }
                        .width(itemWidth)
                        .height(itemHeight),
                    contentAlignment = Alignment.Center
                ) {
                    val movie = items[page]
                    SubcomposeAsyncImage(
                        model = TMDBApi.buildImageUrl(path = movie.backdropPath, size = 500),
                        loading = {
                            CircularProgressIndicator(modifier = Modifier.scale(0.2f))
                        },
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    ),
                                    startY = -10f,
                                    endY = 500f
                                )
                            )
                    ) {
                        Text(
                            text = movie.title,
                            fontSize = 16.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}