package com.nsharma.navigation

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.nsharma.voice.navigation.ContentTransition


class NavigationController internal constructor(
    initialScreen: Screen,
) {
    private val screenStack = java.util.Stack<Screen>()
    private var targetScreen: Screen by mutableStateOf(initialScreen)
    private lateinit var activity: Activity

    private val onBackPressedCallback : OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            targetScreen.onBackPressed()
        }
    }

    init {
        initialScreen.navController = this
        this.screenStack.push(initialScreen)
    }

    @Composable
    internal fun View() {
        val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
        activity = LocalContext.current as Activity

        ContentTransition(
            targetContent = targetScreen,
            modifier = Modifier.fillMaxSize()
        ) {
            enter = s.enterTransition()
            exit = s.exitTransition()
            content = { it.View() }
        }

        DisposableEffect(dispatcher) {
            dispatcher.addCallback(onBackPressedCallback)
            onDispose {
                onBackPressedCallback.remove()
            }
        }
    }

    fun push(screen: Screen) {
        screen.navController = this
        screenStack.push(screen)
        updateNavigationState()
    }

    fun pop() {
        if (screenStack.isNotEmpty()) screenStack.pop()
        updateNavigationState()
    }

    private fun updateNavigationState() {
        if (screenStack.isNotEmpty())
            targetScreen = screenStack.peek()
        else {
            activity.finish()
        }
    }
}


@Composable
fun Navigator(
    initialScreen: Screen
) {
    val navController = remember { NavigationController(initialScreen) }
    navController.View()
}

