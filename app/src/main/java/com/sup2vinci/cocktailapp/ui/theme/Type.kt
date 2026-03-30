package com.sup2vinci.cocktailapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.sup2vinci.cocktailapp.R

val CustomFont = FontFamily(
    Font(R.font.arianavioleta, FontWeight.Normal)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    )
)