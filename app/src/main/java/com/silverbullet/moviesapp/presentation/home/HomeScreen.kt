package com.silverbullet.moviesapp.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.silverbullet.moviesapp.presentation.home.components.SearchBar
import com.silverbullet.moviesapp.presentation.home.components.TopSection
import com.silverbullet.moviesapp.R
import com.silverbullet.moviesapp.presentation.ui.theme.SoftColor

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopSection(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                icon = R.drawable.ic_search,
                backgroundColor = SoftColor,
                hint = "Search a title..",
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            )
        }
    }
}