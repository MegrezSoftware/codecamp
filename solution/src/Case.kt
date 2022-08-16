data class Case(
    val rawInputString: String,
    val cardGroup: MajiangCardGroup,
    val tingCardGroup: MajiangCardGroup,
    val solution: IMajiangSolution,
) {
    fun isValid(): Boolean {
        return cardGroup.isValid() && tingCardGroup.isValid()
    }

    fun test() {
        print()

        if (!isValid()) return

        val tingResult = solution.execute(cardGroup)
        println("计算结果：")
        println(tingResult)
        if (tingResult.toString() == tingCardGroup.toString()) {
            println("pass")
        } else {
            println("fail")
        }
        println()
    }

    fun print() {
        if (!isValid()) {
            println("case: $rawInputString is not valid case")
            println("exist card group: $cardGroup")
            println("ting card group: $cardGroup")
            println()
            return
        }

        println("手牌：")
        println(cardGroup.toString())
        println("听牌：")
        if (tingCardGroup.cardItems.isEmpty()) {
            println("不听牌")
        } else {
            println(tingCardGroup.toString())
        }
    }
}