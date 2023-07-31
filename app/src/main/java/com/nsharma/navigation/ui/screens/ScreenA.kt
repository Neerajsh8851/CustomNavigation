package com.nsharma.navigation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nsharma.navigation.Screen

object ScreenA: Screen() {
    private const val TAG = "GreetingScreen"

    @Composable
    override fun View() {
        Box(modifier = Modifier.fillMaxSize()) {
            Button(onClick = { navigateToScreenB() }, modifier = Modifier.align(Alignment.Center)) {
                Text(text = "Navigate to B")
            }
        }
    }

    private fun navigateToScreenB() {
        navController.push(ScreenB("Press back button to go back!"))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed: ")
    }
}

