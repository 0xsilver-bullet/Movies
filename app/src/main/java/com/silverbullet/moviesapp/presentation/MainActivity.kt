package com.silverbullet.moviesapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.silverbullet.moviesapp.R
import com.silverbullet.moviesapp.navigation.MoviesAppNavHost
import com.silverbullet.moviesapp.navigation.Screen
import com.silverbullet.moviesapp.presentation.components.BottomNavBar
import com.silverbullet.moviesapp.presentation.components.NavItem
import com.silverbullet.moviesapp.presentation.ui.theme.BlueAccent
import com.silverbullet.moviesapp.presentation.ui.theme.MoviesTheme
import com.silverbullet.moviesapp.presentation.ui.theme.SoftColor
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var _navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MoviesTheme {
                val navController = rememberNavController()
                _navController = navController
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
                            LaunchedEffect(key1 = true) {
                                navController.addOnDestinationChangedListener { _, destination, _ ->
                                    selectedRoute = destination.route ?: ""
                                }
                            }
                            NavItem(
                                icon = R.drawable.ic_home,
                                name = "Home",
                                unselectedColor = Color.White,
                                selectedColor = BlueAccent,
                                isSelected = selectedRoute == Screen.HomeScreen.route,
                                backgroundSelectedColor = SoftColor,
                                route = Screen.HomeScreen.route,
                                onSelect = {
                                    navController.navigate(it) {
                                        launchSingleTop = true
                                        popUpTo(Screen.HomeScreen.route)
                                    }
                                }
                            )
                            NavItem(
                                icon = R.drawable.ic_search,
                                name = "Search",
                                unselectedColor = Color.White,
                                selectedColor = BlueAccent,
                                backgroundSelectedColor = SoftColor,
                                isSelected = selectedRoute.takeWhile { it != '/' } == Screen.SearchScreen.route,
                                route = Screen.SearchScreen.route,
                                onSelect = {
                                    navController.navigate(route = "$it/0.0/0.0") {
                                        popUpTo(Screen.HomeScreen.route)
                                    }
                                }
                            )
                            NavItem(
                                icon = R.drawable.ic_favorite,
                                name = "Favorite",
                                unselectedColor = Color.White,
                                selectedColor = BlueAccent,
                                backgroundSelectedColor = SoftColor,
                                isSelected = selectedRoute == Screen.FavoriteScreen.route,
                                route = Screen.FavoriteScreen.route,
                                onSelect = {
                                    navController.navigate(route = Screen.FavoriteScreen.route) {
                                        popUpTo(Screen.HomeScreen.route)
                                    }
                                }
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        _navController.handleDeepLink(intent)
    }
}
