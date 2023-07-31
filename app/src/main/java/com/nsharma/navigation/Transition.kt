package com.nsharma.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun <S> ContentTransition(
    targetContent: S,
    enterTransition: EnterTransition,
    exitTransition: ExitTransition,
    modifier: Modifier = Modifier,
    content: @Composable (S) -> Unit
) {
    val transition = updateTransition(targetState = targetContent, label = "ContentTransition")
    transition.ContentTransition(enterTransition, exitTransition, content, modifier)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <S> Transition<S>.ContentTransition(
    enterTransition: EnterTransition,
    exitTransition: ExitTransition,
    content: @Composable (S) -> Unit,
    modifier: Modifier = Modifier
) {
    val visibleContent = remember { mutableStateListOf(currentState) }

    if (currentState == targetState) {
        visibleContent.clear()
        visibleContent.add(currentState)
    } else {
        visibleContent.clear()
        visibleContent.add(currentState)
        visibleContent.add(targetState)
    }

    visibleContent.forEach { s ->
        key(s) {
            AnimatedVisibility(
                { it == s },
                enter = enterTransition,
                exit = exitTransition,
                modifier = modifier
            ) {
                content(s)
            }
        }
    }
}