package com.nsharma.navigation

import androidx.compose.runtime.Composable

abstract class Screen {
    internal lateinit var navController: NavigationController
    open fun onBackPressed(): Unit = navController.pop()
    @Composable abstract fun View()
}