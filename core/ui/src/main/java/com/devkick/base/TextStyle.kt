package com.devkick.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itbookstore.resources.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal)
)

enum class TextStyleEnum {
    Title,
    Body,
    TextButton,
    HeadLine,
    Caption,
    OverLine,
}

val Int.dp
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

@Composable
fun typography(textStyle: TextStyleEnum): TextStyle {
    return when (textStyle) {
        TextStyleEnum.Title -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 12.dp,
            lineHeight = 16.dp,
        )

        TextStyleEnum.Body -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 12.dp,
            lineHeight = 16.dp,
        )

        TextStyleEnum.TextButton -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 13.dp,
            lineHeight = 18.dp
        )

        TextStyleEnum.HeadLine -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 22.dp,
            lineHeight = 30.dp
        )

        TextStyleEnum.Caption -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 12.dp,
            lineHeight = 16.dp
        )

        TextStyleEnum.OverLine -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 10.dp,
            lineHeight = 14.dp,
        )
    }
}
