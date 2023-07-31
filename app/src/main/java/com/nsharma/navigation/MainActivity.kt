package com.nsharma.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import com.nsharma.navigation.ui.screens.ScreenA

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigator(
                initialScreen = ScreenA,
                transitionResolver = { _, _, isPush ->
                    if (isPush) {
                        slideInHorizontally { it } with slideOutHorizontally { -it }
                    } else {
                        slideInHorizontally { -it } with slideOutHorizontally { it }
                    }
                }
            )
        }
    }
}