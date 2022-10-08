package com.silverbullet.moviesapp.presentation.search

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.silverbullet.moviesapp.R
import com.silverbullet.moviesapp.presentation.components.SearchBar
import com.silverbullet.moviesapp.presentation.ui.theme.SoftColor

/**
 * @param offset is used to play an animation for the search bar in case it was opened from home screen.
 */
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    offset: Offset
) {
    var searchBoxOffset by remember {
        mutableStateOf(offset)
    }
    val offsetValue by animateOffsetAsState(
        targetValue = searchBoxOffset,
        animationSpec = tween(durationMillis = 650)
    )
    LaunchedEffect(key1 = true) {
        searchBoxOffset = Offset.Zero
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            icon = R.drawable.ic_search,
            backgroundColor = SoftColor,
            hint = "Search a title..",
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .offset(
                    x = 0.dp,
                    y = with(LocalDensity.current) { offsetValue.y.toDp() }
                )
        )
    }
}