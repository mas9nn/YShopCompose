package kz.yshop.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import com.plcoding.socialnetworktwitch.presentation.ui.theme.*

private val DarkColorPalette = darkColors(
    primary = GreenAccent,
    background = DarkGray,
    onBackground = TextWhite,
    onPrimary = DarkGray,
    surface = MediumGray,
    onSurface = LightGray
)

@Composable
fun YShopTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}