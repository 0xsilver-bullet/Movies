package com.silverbullet.moviesapp.presentation.search

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.silverbullet.moviesapp.R
import com.silverbullet.moviesapp.navigation.Screen
import com.silverbullet.moviesapp.presentation.components.SearchBar
import com.silverbullet.moviesapp.presentation.home.components.MovieInfoItem
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
        animationSpec = tween(durationMillis = 550)
    )
    val topOffset = with(LocalDensity.current) { 8.dp.toPx() }

    val focusRequester = remember { FocusRequester() }
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        searchBoxOffset = Offset(
            0f,
            topOffset
        )
        if (viewModel.shouldFocusSearchBar) {
            focusRequester.requestFocus()
            viewModel.shouldFocusSearchBar = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // ** NOTICE **
        // height of spacer must be the same value of topOffset
        Spacer(modifier = Modifier.height(8.dp))
        SearchBar(
            icon = R.drawable.ic_search,
            backgroundColor = SoftColor,
            hint = "Search a title..",
            searchText = viewModel.searchTextState.value,
            onSearch = { query -> viewModel.onEvent(SearchScreenEvent.EditSearchQuery(query)) },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .offset(
                    x = 0.dp,
                    y = with(LocalDensity.current) { offsetValue.y.toDp() } - 8.dp
                )
                .focusRequester(focusRequester)
        )
        Spacer(modifier = Modifier.height(32.dp))
        state.searchResult?.let { searchResult ->
            LazyColumn(modifier = Modifier.padding(horizontal = 24.dp)) {
                items(searchResult.movies) { movieInfo ->
                    MovieInfoItem(
                        movieInfo = movieInfo,
                        backgroundColor = SoftColor,
                        modifier = Modifier
                            .padding(horizontal = 28.dp)
                            .fillMaxWidth()
                            .aspectRatio(0.9f)
                            .clickable {
                                navController.navigate(Screen.MovieDetailsScreen.route + "/${movieInfo.id}/${movieInfo.title}")
                            }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}