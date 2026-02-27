package com.example.minitienda.ui.theme

import androidx.compose.ui.graphics.Brush

object AppGradient {
    val Primary = Brush.Companion.horizontalGradient(
        listOf(
            PrimaryBlue,
            PrimaryPurple
        )
    )
}