package com.nsharma.voice.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


@Composable
fun <S> ContentTransition(
    targetContent: S,
    modifier: Modifier = Modifier,
    contentScope: ContentScope<S>.() -> Unit
) {
    val transition = updateTransition(targetState = targetContent, label = "ContentTransition")
    transition.ContentTransition(contentScope, modifier)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <S> Transition<S>.ContentTransition(
    contentScope: ContentScope<S>.() -> Unit,
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
        val content = ContentScope(s)
        content.contentScope()

        key(s) {
            AnimatedVisibility(
                { it == s },
                enter = content.enter,
                exit = content.exit,
                modifier = modifier

            ) {
                content.content(s)
            }
        }
    }
}


class ContentScope<S> (val s: S) {
    var enter: EnterTransition = fadeIn()
    var exit: ExitTransition = fadeOut()
    lateinit var content: @Composable (S) -> Unit
}