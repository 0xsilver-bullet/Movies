package com.silverbullet.moviesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.silverbullet.moviesapp.R
import com.silverbullet.moviesapp.navigation.MoviesAppNavHost
import com.silverbullet.moviesapp.navigation.Screen
import com.silverbullet.moviesapp.presentation.components.BottomNavBar
import com.silverbullet.moviesapp.presentation.components.NavItem
import com.silverbullet.moviesapp.presentation.ui.theme.BlueAccent
import com.silverbullet.moviesapp.presentation.ui.theme.MoviesTheme
import com.silverbullet.moviesapp.presentation.ui.theme.SoftColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(72.dp)
                        ) {
                            var selectedRoute by remember {
                                mutableStateOf(Screen.HomeScreen.route)
                            }
                            NavItem(
                                icon = R.drawable.ic_home,
                                name = "Home",
                                unselectedColor = Color.White,
                                selectedColor = BlueAccent,
                                isSelected = selectedRoute == Screen.HomeScreen.route,
                                backgroundSelectedColor = SoftColor,
                                route = Screen.HomeScreen.route,
                                onSelect = { selectedRoute = it }
                            )
                            NavItem(
                                icon = R.drawable.ic_search,
                                name = "Search",
                                unselectedColor = Color.White,
                                selectedColor = BlueAccent,
                                backgroundSelectedColor = SoftColor,
                                isSelected = selectedRoute == Screen.SearchScreen.route,
                                route = Screen.SearchScreen.route,
                                onSelect = { selectedRoute = it }
                            )
                            NavItem(
                                icon = R.drawable.ic_favorite,
                                name = "Favorite",
                                unselectedColor = Color.White,
                                selectedColor = BlueAccent,
                                backgroundSelectedColor = SoftColor,
                                isSelected = selectedRoute == Screen.FavoriteScreen.route,
                                route = Screen.FavoriteScreen.route,
                                onSelect = { selectedRoute = it }
                            )
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colors.background
                    ) {
                        MoviesAppNavHost(navController = navController)
                    }
                }
            }
        }
    }
}
