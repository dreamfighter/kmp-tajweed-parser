package id.dreamfighter.kmp.tajweed.parser.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import id.dreamfighter.kmp.tajweed.parser.MetaColor
import id.dreamfighter.kmp.tajweed.parser.TajweedParser.parse
import id.dreamfighter.kmp.tajweed.parser.toComposeColor
import id.dreamfighter.kmp.tajweed_parser.generated.resources.Res
import id.dreamfighter.kmp.tajweed_parser.generated.resources.kitab_regular
import org.jetbrains.compose.resources.Font

// IMPORTANT: Define your custom font (Kitab-Regular) if it exists in your project's assets/res/font
// For this example, we'll use a placeholder for the custom font definition.


/**
 * The main Composable function equivalent to the SwiftUI TajweedColors View.
 * It parses the text and displays it with color annotations.
 */
@Composable
fun TajweedText(
    text: String,
    modifier: Modifier = Modifier,
    metaColor: MetaColor = MetaColor(),
    // Replace with your actual custom font definition if available
    fontFamily: FontFamily = FontFamily.Default,
    fontSize: Int = 24
) {
    val kitabFontFamily = FontFamily(Font(Res.font.kitab_regular, FontWeight.Normal, FontStyle.Normal)) // Uncomment and fix R.font
    // 1. Process the text
    val string = text
    val textsAyah = parse(rawAyah = string, metaColor = metaColor)

    // 2. Build the AnnotatedString
    val annotatedText = buildAnnotatedString {
        for (ayah in textsAyah) {
            println(ayah.color)
            val color = ayah.color.toComposeColor()

            // Append the text segment with the specific color style
            append(ayah.text)
            addStyle(
                style = SpanStyle(color = color),
                start = length - ayah.text.length,
                end = length
            )
        }
    }

    // 3. Display the result
    Text(
        text = annotatedText,
        modifier = modifier,
        style = TextStyle(
            fontSize = fontSize.sp,
            fontFamily = kitabFontFamily // Apply the custom font family
        )
    )
}