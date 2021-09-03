package kz.yshop.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.plcoding.socialnetworktwitch.presentation.ui.theme.TextWhite

//val quicksand = FontFamily(
//    Font(R.font.rob, FontWeight.Light),
//    Font(R.font.quicksand_regular, FontWeight.Normal),
//    Font(R.font.quicksand_medium, FontWeight.Medium),
//    Font(R.font.quicksand_semibold, FontWeight.SemiBold),
//    Font(R.font.quicksand_bold, FontWeight.Bold),
//)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = TextWhite
    ),
    h1 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = TextWhite
    ),
    h2 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        color = TextWhite
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = TextWhite
    )
)