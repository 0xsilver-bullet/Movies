package com.silverbullet.moviesapp.presentation.components

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentXKt
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
fun YoutubeView(
    modifier: Modifier = Modifier,
    apiKey: String,
    videoKey: String
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var ytPlayer by remember {
        mutableStateOf<YouTubePlayer?>(null)
    }
    var ytPlayerActiveState by remember {
        mutableStateOf(false)
    }
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                ytPlayerActiveState = false
            } else if (event == Lifecycle.Event.ON_RESUME) {
                ytPlayerActiveState = true
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            ytPlayerActiveState = false
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    LaunchedEffect(key1 = ytPlayerActiveState) {
        if (ytPlayerActiveState) {
            delay(1000L)
            ytPlayer?.cueVideo(videoKey)
        } else {
            ytPlayer?.release()
        }
    }
    AndroidView(
        factory = {
            val fm = (context as AppCompatActivity).supportFragmentManager
            val view = FragmentContainerView(it).apply {
                id = androidx.fragment.R.id.fragment_container_view_tag
            }
            val fragment = YouTubePlayerSupportFragmentXKt().apply {
                initialize(
                    apiKey,
                    object : YouTubePlayer.OnInitializedListener {
                        override fun onInitializationSuccess(
                            provider: YouTubePlayer.Provider?,
                            player: YouTubePlayer?,
                            wasRestored: Boolean
                        ) {
                            ytPlayer = player
                            player?.setShowFullscreenButton(false)
                        }

                        override fun onInitializationFailure(
                            p0: YouTubePlayer.Provider?,
                            p1: YouTubeInitializationResult?
                        ) {
                            Timber.d("Error Initializing Youtube player")
                        }
                    }
                )
            }
            fm.commit {
                setReorderingAllowed(true)
                add(androidx.fragment.R.id.fragment_container_view_tag, fragment)
            }
            view
        },
        modifier = modifier
    )
}
