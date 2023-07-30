package com.nsharma.navigation.ui.screens

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nsharma.navigation.Screen

data class ScreenB(val message: String) : Screen() {
    override fun enterTransition(): EnterTransition = slideInHorizontally { it }
    override fun exitTransition(): ExitTransition = slideOutHorizontally { it }

    @Composable
    override fun View() {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = message, modifier = Modifier.align(Alignment.Center))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed")
    }

    companion object {
        const val TAG = "MessageScreen"
    }
}