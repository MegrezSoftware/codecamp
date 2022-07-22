interface ISolution {
    fun execute(existCard: ExistCard): DependentCard
}

class TestCase(private val case: String) {
    private val cards = case.substringBefore(",").split("-")
    private val result = case.substringAfter(",")

    init {
        require(cards.size >= 3)
    }

    private fun wanCards(): List<String> {
        return cards[0].toCharArray().map { it.toString() }
    }

    private fun tongCards(): List<String> {
        return cards[1].toCharArray().map { it.toString() }
    }

    private fun tiaoCards(): List<String> {
        return cards[2].toCharArray().map { it.toString() }
    }

    private fun anyCards(): List<String> {
        return cards.getOrNull(3)?.toCharArray()?.map { it.toString() } ?: emptyList()
    }

    fun test(solution: ISolution) {
        val executeLog = StringBuilder()
        executeLog.appendLine("execute case: $case")
        val wanCardGroup = CardGroup(wanCards())
        val tongCardGroup = CardGroup(tongCards())
        val tiaoCard = CardGroup(tiaoCards())
        val existCard = ExistCard(listOf(wanCardGroup, tongCardGroup, tiaoCard))
        val dependentCardResult = solution.execute(existCard).genResultString()
        executeLog.appendLine("actual result:${dependentCardResult}")
        executeLog.appendLine("expect result:$result")
        if (dependentCardResult == result) {
            executeLog.appendLine("--pass--")
        } else {
            executeLog.appendLine("--fail--")
        }
        executeLog.appendLine()
        println(executeLog)
    }

}