package com.laura.eventpro.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EventProColors = lightColorScheme(
    primary = Rose,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = Gold,
    background = Cream,
    surface = androidx.compose.ui.graphics.Color.White,
    onSurface = Ink,
    primaryContainer = RoseLight
)

@Composable
fun EventProTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = EventProColors, content = content)
}
