package org.tawhid.airwaves.theme

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

@Composable
fun LexendTypography() = Typography().run {

    val fontFamily = LexendFontFamily()

    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily =  fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}