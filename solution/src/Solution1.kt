fun main(args: Array<String>) {
    val solution = Solution1()
    TestCase("223344-234-2234,--25").test(solution)
    TestCase("1112345678999--,123456789--").test(solution)
    TestCase("23456-22233-789,147--").test(solution)
    TestCase("123456-23444-55,-14-5").test(solution)
    TestCase("1112378999-123-,1469--").test(solution)
    TestCase("2344445688999--,1478--").test(solution)
}

data class Input(
    val wanCards: List<String>,
    val tongCards: List<String>,
    val tiaoCards: List<String>,
    val anyCards: List<String>,
)

data class Output(
    val wanResults: List<String> = emptyList(),
    val tongResults: List<String> = emptyList(),
    val tiaoResults: List<String> = emptyList(),
) {
    fun merge(output: Output): Output {
        return Output(
            wanResults.toMutableSet().also { it.addAll(output.wanResults) }.toList().sorted(),
            tongResults.toMutableSet().also { it.addAll(output.tongResults) }.toList().sorted(),
            tiaoResults.toMutableSet().also { it.addAll(output.tiaoResults) }.toList().sorted(),
        )
    }

    override fun toString(): String {

        return "${wanResults.toSolutionResult()}-${tongResults.toSolutionResult()}-${tiaoResults.toSolutionResult()}"
    }
}

class Solution1 : ISolution {
    fun findOneSolution(input: Input): Output {
        val wanCheck = input.wanCards.checkShunOrKe()
        val tongCheck = input.tongCards.checkShunOrKe()
        val tiaoCheck = input.tiaoCards.checkShunOrKe()

        val wanResult: List<String> = if (tongCheck && tiaoCheck) {
            input.wanCards.getListenResult()
        } else emptyList()

        val tongResult: List<String> = if (wanCheck && tiaoCheck) {
            input.tongCards.getListenResult()
        } else emptyList()

        val tiaoResult: List<String> = if (wanCheck && tongCheck) {
            input.tiaoCards.getListenResult()
        } else emptyList()

        return Output(wanResult, tongResult, tiaoResult)
    }

    override fun execute(input: Input): String {
        val output1 = (1..9).map { it.toString() }.mapNotNull { num ->
            if (input.wanCards.count { it == num } >= 2) {
                val wans = input.wanCards.toMutableList().apply {
                    remove(num)
                    remove(num)
                }
                findOneSolution(input.copy(wanCards = wans))
            } else {
                null
            }
        }

        val output2 = (1..9).map { it.toString() }.mapNotNull { num ->
            if (input.tongCards.count { it == num } >= 2) {
                val tongs = input.tongCards.toMutableList().apply {
                    remove(num)
                    remove(num)
                }
                findOneSolution(input.copy(tongCards = tongs))
            } else {
                null
            }
        }

        val output3: List<Output> = (1..9).map { it.toString() }.mapNotNull { num ->
            if (input.tiaoCards.count { it == num } >= 2) {
                val tiaos = input.tiaoCards.toMutableList().apply {
                    remove(num)
                    remove(num)
                }
                findOneSolution(input.copy(tiaoCards = tiaos))
            } else {
                null
            }
        }


        val result = listOf(output1, output2, output3).flatten().fold(Output()) { result, item ->
            result.merge(item)
        }

        return result.toString()
    }

//    fun execute(
//        wanCards: List<String>,
//        tongCards: List<String>,
//        tiaoCards: List<String>,
//        anyCards: List<String>,
//    ): String {
//        val wanCheck = wanCards.checkShunOrKe()
//        val tongCheck = tongCards.checkShunOrKe()
//        val tiaoCheck = tiaoCards.checkShunOrKe()
//
//        val wanResult: List<String> = if (tongCheck && tiaoCheck) {
//            (1..9).filter { wanCards.checkListen(it) }.map { it.toString() }
//        } else emptyList()
//
//        val tongResult: List<String> = if (wanCheck && tiaoCheck) {
//            (1..9).filter { tongCards.checkListen(it) }.map { it.toString() }
//        } else emptyList()
//
//        val tiaoResult: List<String> = if (wanCheck && tongCheck) {
//            (1..9).filter { tiaoCards.checkListen(it) }.map { it.toString() }
//        } else emptyList()
//
//        return "${wanResult.toSolutionResult()}-${tongResult.toSolutionResult()}-${tiaoResult.toSolutionResult()}"
//    }

}

fun List<String>.toSolutionResult(): String {
    return joinToString(separator = "") { it }
}

/**
 * 检测是否符合顺或者刻
 */
fun List<String>.checkShunOrKe(): Boolean {
    if (size % 3 != 0) return false

    val cards = sorted().toMutableList()
    (1..9).forEach { num ->
        while (cards.count { it == num.toString() } >= 3) {
            cards.remove(num.toString())
            cards.remove(num.toString())
            cards.remove(num.toString())
        }
    }

    (1..7).forEach { num ->
        while (cards.contains(num.toString()) &&
            cards.contains((num + 1).toString()) &&
            cards.contains((num + 2).toString())
        ) {
            cards.remove(num.toString())
            cards.remove((num + 1).toString())
            cards.remove((num + 2).toString())
        }
    }

    return cards.isEmpty()

//    val cards = sorted().toMutableList()
//    (1..7).forEach { num ->
//        while (cards.contains(num.toString()) &&
//            cards.contains((num + 1).toString()) &&
//            cards.contains((num + 2).toString())
//        ) {
//            cards.remove(num.toString())
//            cards.remove((num + 1).toString())
//            cards.remove((num + 2).toString())
//        }
//    }
//    (1..9).forEach { num ->
//        if (cards.count { it == num.toString() } % 3 != 0) {
//            return false
//        }
//    }
//    return true
}

fun List<String>.getListenResult(): List<String> {
    if (size % 3 != 2) return emptyList()
    return (1..9).filter { num ->
        val cards = toMutableList().also { it.add(num.toString()) }
        cards.checkShunOrKe()
    }.map { it.toString() }
}

/**
 * 检测是否听牌
 */
//fun List<String>.checkListen(targetNum: Int): Boolean {
//    if (size % 3 != 1) return false
//    return (1..9).any { num ->
//        val cards = toMutableList().also { it.add(targetNum.toString()) }
//        if (count { it == num.toString() } < 2) {
//            false
//        } else {
//            cards.remove(num.toString())
//            cards.remove(num.toString())
//            cards.checkShunOrKe()
//        }
//    }
//}

