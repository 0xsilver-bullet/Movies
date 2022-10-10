package com.silverbullet.moviesapp.presentation.movie_details

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.silverbullet.moviesapp.data.remote.TMDBApi
import com.silverbullet.moviesapp.presentation.movie_details.components.MovieDetailsTopSection
import com.silverbullet.moviesapp.presentation.ui.theme.DarkColor
import com.silverbullet.moviesapp.R
import com.silverbullet.moviesapp.presentation.movie_details.components.DetailItem
import com.silverbullet.moviesapp.presentation.movie_details.components.StoryLineSection
import com.silverbullet.moviesapp.presentation.ui.theme.Montserrat
import com.silverbullet.moviesapp.presentation.ui.theme.OrangeColor
import com.silverbullet.moviesapp.presentation.ui.theme.SoftColor
import com.silverbullet.moviesapp.utils.Constants.APP_URI

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    navController: NavController,
    movieTitle: String
) {
    val state = viewModel.state.value
    val scrollState = rememberScrollState()

    val shareRequest =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {

        }

    Box(modifier = Modifier.fillMaxSize()) {
        state.movieDetails?.let {
            AsyncImage(
                model = TMDBApi.buildImageUrl(it.posterPath, 500),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(0.75f)
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                DarkColor.copy(alpha = 0.6f),
                                DarkColor
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .fillMaxSize()
                    .background(DarkColor)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                color = Color.Yellow,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Column(modifier = Modifier.fillMaxSize()) {
            MovieDetailsTopSection(
                rightIcon = R.drawable.ic_heart,
                rightIconColor = state.movieDetails?.let { if (it.favorite) Color.Red else Color.White }
                    ?: Color.White,
                rightIconCallback = { viewModel.toggleFavorite() },
                leftIcon = R.drawable.ic_back,
                title = movieTitle,
                leftIconCallback = { navController.navigateUp() }
            )
            state.movieDetails?.let { movieDetails ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = TMDBApi.buildImageUrl(movieDetails.posterPath, 500),
                        contentDescription = null,
                        modifier = Modifier
                            .width(205.dp)
                            .height(287.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            DetailItem(
                                icon = R.drawable.ic_calendar,
                                text = movieDetails.releaseDate
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(16.dp)
                                    .width(1.dp)
                                    .background(Color.Gray)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            DetailItem(
                                icon = R.drawable.ic_movie,
                                text = movieDetails.genres[0].name
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
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
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SoftColor)
                            .clickable {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "$APP_URI/${movieDetails.id}/${
                                            movieDetails.title.replace(
                                                ' ',
                                                '+'
                                            )
                                        }"
                                    )
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)
                                shareRequest.launch(shareIntent)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    StoryLineSection(
                        story = movieDetails.overview,
                        textColor = Color(0xFFEBEBEF),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}