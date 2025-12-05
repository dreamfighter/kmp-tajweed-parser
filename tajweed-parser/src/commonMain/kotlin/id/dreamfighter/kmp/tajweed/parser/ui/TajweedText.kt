package id.dreamfighter.kmp.tajweed.parser.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import id.dreamfighter.kmp.tajweed.parser.TajweedType
import id.dreamfighter.kmp.tajweed.parser.TajweedParser

@Composable
fun TajweedText(rawAyahText: String) {
    val tokens = TajweedParser.parse(rawAyahText)

    val styledText = buildAnnotatedString {
        tokens.forEach { token ->
            val color = getTajweedColor(token.type)
            withStyle(style = SpanStyle(color = color)) {
                append(token.text)
            }
        }
    }

    Text(text = styledText)
}

// Default colors matching the original library (approximate values)
fun getTajweedColor(type: TajweedType): Color {
    return when (type) {
        TajweedType.HAMZAT_WASL,
        TajweedType.SILENT,
        TajweedType.LAM_SHAMSIYYAH -> Color(0xFFAAAAAA) // Grey

        TajweedType.MADDA_NORMAL -> Color(0xFF537FFF) // Light Blue
        TajweedType.MADDA_PERMISSIBLE -> Color(0xFF4050FF) // Blue
        TajweedType.MADDA_NECESSARY -> Color(0xFF000EBC) // Dark Blue
        TajweedType.MADDA_OBLIGATORY -> Color(0xFF2144C1) // Deep Blue

        TajweedType.QALQALAH -> Color(0xFFDD0008) // Red

        TajweedType.IKHFA_SHAFAWI -> Color(0xFFD500B7) // Pink/Purple
        TajweedType.IKHFA -> Color(0xFF9400A8) // Purple

        TajweedType.IDGHAM_SHAFAWI -> Color(0xFF58B800) // Lime Green
        TajweedType.IDGHAM_WITH_GHUNNAH -> Color(0xFF169777) // Teal
        TajweedType.IDGHAM_WITHOUT_GHUNNAH -> Color(0xFF169200) // Green

        TajweedType.IQLAB -> Color(0xFF26BFFD) // Cyan

        TajweedType.GHUNNAH -> Color(0xFFFF7E1E) // Orange

        TajweedType.IDGHAM_MUTAJANISAYN,
        TajweedType.IDGHAM_MUTAQARIBAYN -> Color(0xFFA1A1A1) // Greyish

        TajweedType.NORMAL -> Color.Black // Or your theme's onSurface color
    }
}