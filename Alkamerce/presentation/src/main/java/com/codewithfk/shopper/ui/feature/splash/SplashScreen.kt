package com.codewithfk.shopper.ui.feature.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.codewithfk.shopper.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    // State for the animation
    val scale = remember { Animatable(0f) }

    // Animation effect
    LaunchedEffect(key1 = true) {
        // First grow the logo
        scale.animateTo(
            targetValue = 1.2f,
            animationSpec = tween(
                durationMillis = 500,
                easing = EaseInOut
            )
        )

        // Then shrink it a bit
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(
                durationMillis = 300,
                easing = EaseInOut
            )
        )

        // Wait for a moment before navigating away
        delay(1000)
        onSplashComplete()
    }

    // Splash screen UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.light_logo), // Make sure to add your logo to drawable resources
            contentDescription = "App Logo",
            modifier = Modifier
                .size(300.dp)
                .scale(scale.value)
        )
    }
}