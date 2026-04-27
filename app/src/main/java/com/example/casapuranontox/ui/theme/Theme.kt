package com.example.casapuranontox.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext




val Green700   = Color(0xFF5C7A5C)
val GreenLight = Color(0xFFE8F0E4)
val Cream      = Color(0xFFFAF7F2)
val Brown900   = Color(0xFF2C2C2A)
val Brown400   = Color(0xFF9A9080)
val DividerColor = Color(0xFFE0D9CF)

private val ColorScheme = lightColorScheme(
    primary    = Green700,
    background = Cream,
    surface    = Color.White,
    onBackground = Color(0xFF3A3530),
    onSurface    = Color(0xFF3A3530),
    outline    = DividerColor




    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun CasapuranontoxTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        content     = content
    )
}