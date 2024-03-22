package com.example.memoapp10.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.memoapp10.R

val body= FontFamily(
    Font(R.font.body_nunito, FontWeight.Normal)
)
val memoHeading = FontFamily(
    Font(R.font.quicksand_semibold, FontWeight.Bold)
)
val listHeading = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = body,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayMedium = TextStyle(
        fontFamily = memoHeading,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = listHeading,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        letterSpacing = 0.25.sp

    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)