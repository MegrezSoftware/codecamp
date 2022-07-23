import java.io.File

class MajiangCaseReader(private val fileName: String) {
    private var cases: List<Case> = emptyList()

    fun read(): List<Case> {
        return File(fileName).readLines()
            .map { convertCase(it) }.apply {
                cases = this
            }
    }

    fun print() {
        cases.forEach { it.print() }
    }

    private fun convertCase(content: String): Case {
        val existCardsString = content.split(",")[0]
        val tingCardsString = content.split(",")[1]
        return Case(convertCardGroup(existCardsString), convertCardGroup(tingCardsString).excludePowerCardGroup())
    }

    private fun convertCardGroup(content: String): MajiangCardGroup {
        if (content == "null") return MajiangCardGroup.empty()
        if (content == "all") return MajiangCardGroup.allCards()

        val numberStrings = content.split("-")
        val cards1: List<MajiangCardItem> = numberStrings.getOrNull(0)?.toCharArray()?.map { it.toString().toInt() }
            ?.map { MajiangCardItem(number = MajiangNumber.parse(it), type = MajiangType.WAN) } ?: emptyList()
        val cards2: List<MajiangCardItem> = numberStrings.getOrNull(1)?.toCharArray()?.map { it.toString().toInt() }
            ?.map { MajiangCardItem(number = MajiangNumber.parse(it), type = MajiangType.TONG) } ?: emptyList()
        val cards3: List<MajiangCardItem> = numberStrings.getOrNull(2)?.toCharArray()?.map { it.toString().toInt() }
            ?.map { MajiangCardItem(number = MajiangNumber.parse(it), type = MajiangType.TIAO) } ?: emptyList()
        val cards4: List<MajiangCardItem> = numberStrings.getOrNull(3)?.toCharArray()?.map { it.toString().toInt() }
            ?.map { MajiangCardItem(word = MajiangWord.parse(it)) } ?: emptyList()
        val cards5: List<MajiangCardItem> = numberStrings.getOrNull(4)?.toCharArray()?.map { it.toString().toInt() }
            ?.map { MajiangCardItem(power = PowerType.parse(it)) } ?: emptyList()
        val cards = mutableListOf(cards1, cards2, cards3, cards4, cards5).flatten()
        return MajiangCardGroup(cards)
    }
}

data class Case(
    val cardGroup: MajiangCardGroup,
    val tingCardGroup: MajiangCardGroup
) {
    fun print() {
        println("手牌：")
        println(cardGroup.toString())
        println("听牌：")
        if (tingCardGroup.cardItems.isEmpty()) {
            println("不听牌")
        } else {
            println(tingCardGroup.toString())
        }
        println()
    }
}

interface IMajiangSolution {
    fun execute()
}