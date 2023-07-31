package com.nsharma.navigation

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext


class NavigationController internal constructor(
    initialScreen: Screen,
    private val transitionResolver: TransitionResolver<Screen>
) {
    private val screenStack = java.util.Stack<Screen>()
    private var targetScreen: Screen by mutableStateOf(initialScreen)
    private lateinit var activity: Activity

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                targetScreen.onBackPressed()
            }
        }

    @OptIn(ExperimentalAnimationApi::class)
    private var transition by mutableStateOf(fadeIn() with fadeOut())

    init {
        initialScreen.navController = this
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    internal fun View() {
        val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
        activity = LocalContext.current as Activity

        ContentTransition(
            targetContent = targetScreen,
            modifier = Modifier.fillMaxSize(),
            enterTransition = transition.targetContentEnter,
            exitTransition = transition.initialContentExit,
            content = { screen -> screen.View() }
        )

        DisposableEffect(dispatcher) {
            dispatcher.addCallback(onBackPressedCallback)
            onDispose {
                onBackPressedCallback.remove()
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun push(screen: Screen) {
        screen.navController = this
        screenStack.push(targetScreen)
        val current = targetScreen
        targetScreen = screen
        transition = transitionResolver.resolve(current, targetScreen, true)
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun pop() {
        if (screenStack.isNotEmpty()) {
            val current = targetScreen
            targetScreen = screenStack.pop()
            transition = transitionResolver.resolve(current, targetScreen,false)
        } else {
            activity.finish()
        }
    }
}


fun interface TransitionResolver<S> {
    @OptIn(ExperimentalAnimationApi::class)
    fun resolve(s1: S, s2: S, isPush: Boolean): ContentTransform
}

@Composable
fun Navigator(
    initialScreen: Screen,
    transitionResolver: TransitionResolver<Screen>
) {
    val navController = remember { NavigationController(initialScreen, transitionResolver) }
    navController.View()
}

