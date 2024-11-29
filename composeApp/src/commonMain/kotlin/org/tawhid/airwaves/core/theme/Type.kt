package org.tawhid.airwaves.core.theme

import airwaves.composeapp.generated.resources.Lexend_bold
import airwaves.composeapp.generated.resources.Lexend_light
import airwaves.composeapp.generated.resources.Lexend_medium
import airwaves.composeapp.generated.resources.Lexend_regular
import airwaves.composeapp.generated.resources.Lexend_semibold
import airwaves.composeapp.generated.resources.Res
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LexendFontFamily() = FontFamily(
    Font(Res.font.Lexend_light, weight = FontWeight.Light),
    Font(Res.font.Lexend_regular, weight = FontWeight.Normal),
    Font(Res.font.Lexend_medium, weight = FontWeight.Medium),
    Font(Res.font.Lexend_semibold, weight = FontWeight.SemiBold),
    Font(Res.font.Lexend_bold, weight = FontWeight.Bold)
)

val bodyFontFamily = FontFamily(

)

val displayFontFamily = FontFamily(

)

val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
)