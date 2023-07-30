package com.nsharma.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable

abstract class Screen {
    internal lateinit var navController: NavigationController
    open fun enterTransition() = fadeIn()
    open fun exitTransition() = fadeOut()
    open fun onBackPressed(): Unit = navController.pop()
    @Composable abstract fun View()
}