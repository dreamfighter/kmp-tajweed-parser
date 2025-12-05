package id.dreamfighter.kmp.tajweed.parser

object TajweedParser {

    private val TAG_START = "["
    private val TAG_END = "]"

    /**
     * Parses the raw string from AlQuran Cloud API into a list of Tokens.
     */
    fun parse(rawText: String): List<TajweedToken> {
        if (rawText.isEmpty()) return emptyList()

        val tokens = mutableListOf<TajweedToken>()
        var currentIndex = 0

        while (currentIndex < rawText.length) {
            val tagStartIndex = rawText.indexOf(TAG_START, currentIndex)

            if (tagStartIndex == -1) {
                // No more tags, add the remaining text as NORMAL
                val remainingText = rawText.substring(currentIndex)
                if (remainingText.isNotEmpty()) {
                    tokens.add(TajweedToken(remainingText, TajweedType.NORMAL))
                }
                break
            }

            // Add any text before the tag as NORMAL
            if (tagStartIndex > currentIndex) {
                val normalText = rawText.substring(currentIndex, tagStartIndex)
                tokens.add(TajweedToken(normalText, TajweedType.NORMAL))
            }

            // Extract the tag character
            val tagCharIndex = tagStartIndex + TAG_START.length
            if (tagCharIndex >= rawText.length) {
                // Invalid tag, treat the rest as NORMAL
                val remainingText = rawText.substring(currentIndex)
                tokens.add(TajweedToken(remainingText, TajweedType.NORMAL))
                break
            }

            val tagChar = rawText[tagCharIndex].toString()
            val tajweedType = getRuleFromChar(tagChar)

            // Find the end of the tag (next '[')
            val tagEndIndex = tagCharIndex + 1

            //Move current index to after the tag
            currentIndex = tagEndIndex

            //If tagEndIndex is out of bounds, it will default to empty string
            val nextTagStartIndex = rawText.indexOf(TAG_START,currentIndex)
            val tokenEndIndex = if(nextTagStartIndex == -1) rawText.length else nextTagStartIndex

            if(tokenEndIndex > currentIndex){
                val taggedText = rawText.substring(currentIndex, tokenEndIndex)
                tokens.add(TajweedToken(taggedText, tajweedType))
            }

            currentIndex = tokenEndIndex
        }

        return tokens
    }

    private fun getRuleFromChar(char: String): TajweedType {
        return when (char) {
            "h" -> TajweedType.HAMZAT_WASL
            "s" -> TajweedType.SILENT
            "l" -> TajweedType.LAM_SHAMSIYYAH
            "n" -> TajweedType.MADDA_NORMAL
            "p" -> TajweedType.MADDA_PERMISSIBLE
            "m" -> TajweedType.MADDA_NECESSARY
            "o" -> TajweedType.MADDA_OBLIGATORY
            "q" -> TajweedType.QALQALAH
            "c" -> TajweedType.IKHFA_SHAFAWI
            "f" -> TajweedType.IKHFA
            "w" -> TajweedType.IDGHAM_SHAFAWI
            "i" -> TajweedType.IQLAB
            "a" -> TajweedType.IDGHAM_WITH_GHUNNAH
            "u" -> TajweedType.IDGHAM_WITHOUT_GHUNNAH
            "d" -> TajweedType.IDGHAM_MUTAJANISAYN
            "b" -> TajweedType.IDGHAM_MUTAQARIBAYN
            "g" -> TajweedType.GHUNNAH
            else -> TajweedType.NORMAL
        }
    }
}