package id.dreamfighter.kmp.tajweed.parser

/**
 * Represents the specific Tajweed rule found in the text.
 */
enum class TajweedType {
    NORMAL,                 // Regular text (no specific rule)
    HAMZAT_WASL,            // [h - Hamzat ul Wasl
    SILENT,                 // [s - Silent letter
    LAM_SHAMSIYYAH,         // [l - Lam Shamsiyyah
    MADDA_NORMAL,           // [n - Normal Prolongation (2 vowels)
    MADDA_PERMISSIBLE,      // [p - Permissible Prolongation (2, 4, 6 vowels)
    MADDA_NECESSARY,        // [m - Necessary Prolongation (6 vowels)
    MADDA_OBLIGATORY,       // [o - Obligatory Prolongation (4-5 vowels)
    QALQALAH,               // [q - Qalqalah (Echoing sound)
    IKHFA_SHAFAWI,          // [c - Ikhfa Shafawi (with Meem)
    IKHFA,                  // [f - Ikhfa (Hiding)
    IDGHAM_SHAFAWI,         // [w - Idgham Shafawi (with Meem)
    IQLAB,                  // [i - Iqlab (Conversion)
    IDGHAM_WITH_GHUNNAH,    // [a - Idgham with Ghunnah
    IDGHAM_WITHOUT_GHUNNAH, // [u - Idgham without Ghunnah
    IDGHAM_MUTAJANISAYN,    // [d - Idgham Mutajanisayn
    IDGHAM_MUTAQARIBAYN,    // [b - Idgham Mutaqaribayn
    GHUNNAH                 // [g - Ghunnah
}

/**
 * A single segment of text with its associated rule.
 */
data class TajweedToken(
    val text: String,
    val type: TajweedType
)