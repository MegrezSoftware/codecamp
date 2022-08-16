import java.io.File

class MajiangCaseReader(private val fileName: String, private val solution: IMajiangSolution) {
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

    fun test() {
        println("case file: $fileName")
        cases.forEach { it.test() }
    }

    fun testIndex(index: Int) {
        cases.getOrNull(index)?.test()
    }

    private fun convertCase(content: String): Case {
        val existCardsString = content.split(",")[0]
        val tingCardsString = content.split(",")[1]
        return Case(
            content,
            convertCardGroup(existCardsString),
            convertCardGroup(tingCardsString).excludePowerCardGroup(),
            solution
        )
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
            ?.map { MajiangCardItem(flower = MajiangFlower.parse(it)) } ?: emptyList()
        val cards = mutableListOf(cards1, cards2, cards3, cards4, cards5).flatten()
        return MajiangCardGroup(cards)
    }
}

