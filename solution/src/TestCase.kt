interface ISolution {
    fun execute(input: Input): String
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
        val actualResult = solution.execute(
            Input(
                wanCards(),
                tongCards(),
                tiaoCards(),
                anyCards()
            )
        )
        executeLog.appendLine("actual result:$actualResult")
        executeLog.appendLine("expect result:$result")
        if (actualResult == result) {
            executeLog.appendLine("--pass--")
        } else {
            executeLog.appendLine("--fail--")
        }
        executeLog.appendLine()
        println(executeLog)
    }

}