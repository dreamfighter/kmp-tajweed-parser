package id.dreamfighter.kmp.tajweed.parser

import androidx.compose.ui.graphics.Color

object TajweedParser {


    fun parse(rawAyah: String, metaColor: MetaColor): List<TajweedAyah> {
        val datas = mutableListOf<TajweedAyah>()
        val tajweedMetas = "hslnpmqocfwiaudbg"
        println("rawayah ($rawAyah) ")


        var splits = mutableListOf<String>()
        var prev = ""
        var curr = ""
        var temp = ""
        var index = 0
        //val aryChar = rawAyah.get(0)
        while (index < rawAyah.length) {
            curr = "${rawAyah[index]}"
            if (prev == "[" && tajweedMetas.contains(curr)) {
                splits.add(temp)
                splits.add(curr)
                temp = ""
                while (curr != "[" && index < rawAyah.length) {
                    index += 1
                    curr = "${rawAyah[index]}"
                }
            } else if (prev == "]") {
                splits.add(temp)
                temp = ""
            } else if (prev != "[") {
                temp = "$temp$prev"
                println("curr => $prev")
            }

            prev = curr
            index += 1
        }
        if (prev != "]") {
            temp = "$temp$prev"
        }
        splits.add(temp)

        var metaSpilt: String = ""
        var i: Int = 0
        for (ayahSpilt in splits) {
            println("ayahSpilt ($ayahSpilt)")
            if (tajweedMetas.contains(ayahSpilt)) {
                metaSpilt = ayahSpilt
            } else if (!metaSpilt.isEmpty()) {
                val metaColor = metaToColor(metaSpilt.first(), metaColor)

                datas.add(TajweedAyah(i, metaColor, ayahSpilt))
                metaSpilt = ""
                i += 1
            } else {
                datas.add(TajweedAyah(i, "#000000", ayahSpilt))
                i += 1
            }
        }
        return datas
    }
}

data class TajweedAyah(
    val id: Int,
    val color: String, // Keep as String for flexibility until consumption
    val text: String
)

data class MetaColor(
    // hamza-wasl, silent, laam-shamsiyah
    val hsl: String = "#000000",

    // Normal Prolongation: 2 Vowels
    val madda_normal: String = "#537FFF",

    // Permissible Prolongation: 2, 4, 6 Vowels
    val madda_permissible: String = "#4050FF",

    // Necessary Prolongation: 6 Vowels
    val madda_necesssary: String = "#000EBC",

    // Obligatory Prolongation: 4-5 Vowels
    val madda_obligatory: String = "#2144C1",

    // Qalaqah
    val qalaqah: String = "#DD0008",

    // Ikhafa' Shafawi - With Meem
    val ikhafa_shafawi: String = "#D500B7",

    // Ikhafa'
    val ikhafa: String = "#9400A8",

    // Idgham Shafawi - With Meem
    val idgham_shafawi: String = "#58B800",

    // Iqlab
    val iqlab: String = "#26BFFD",

    // Idgham - With Ghunnah
    val idgham_with_ghunnah: String = "#169777",

    // Idgham - Without Ghunnah
    val idgham_without_ghunnah: String = "#169200",

    // Idgham - Mutajanisayn
    val idgham_mutajanisayn: String = "#A1A1A1",

    // Idgham - Mutaqaribayn
    val idgham_mutaqaribayn: String = "#A1A1A1",

    // Ghunnah: 2 Vowels
    val ghunnah: String = "#FF7E1E"
)


fun metaToColor(meta: Char, metaColor: MetaColor): String {
    return when (meta) {
        'h', 'l', 's' -> metaColor.hsl // hamza-wasl, silent, laam-shamsiyah
        'n' -> metaColor.madda_normal
        'p' -> metaColor.madda_permissible
        'm' -> metaColor.madda_necesssary
        'q' -> metaColor.qalaqah
        'o' -> metaColor.madda_obligatory
        'c' -> metaColor.ikhafa_shafawi
        'f' -> metaColor.ikhafa
        'w' -> metaColor.idgham_shafawi
        'i' -> metaColor.iqlab
        'a' -> metaColor.idgham_with_ghunnah
        'u' -> metaColor.idgham_without_ghunnah
        'd' -> metaColor.idgham_mutajanisayn
        'b' -> metaColor.idgham_mutaqaribayn
        'g' -> metaColor.ghunnah
        else -> "#000000"
    }
}

// Convert Hex string to Compose Color
fun String.toComposeColor(): Color {
    val hex = this.removePrefix("#").uppercase()
    return try {
        val colorInt = hex.toLong(16).toInt()
        Color(colorInt or 0xFF000000.toInt()) // Ensure full opacity
    } catch (e: Exception) {
        print(e)
        Color.Black
    }
}