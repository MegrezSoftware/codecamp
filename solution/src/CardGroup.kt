fun createEmptyDependentGroup(result: CardGroupCheckResult): ChangeGroupTypeDependency {
    return ChangeGroupTypeDependency(emptyList(), result)
}

/**
 * 手牌组
 */
data class CardGroup(private val cards: List<String>) {
    val checkResult: CardGroupCheckResult by lazy {
        checkGroup()
    }

    fun changeToDuiGroupDependency(): ChangeGroupTypeDependency {
        return when (checkResult) {
            CardGroupCheckResult.SHUN_OR_KE_GROUP -> createEmptyDependentGroup(CardGroupCheckResult.SHUN_OR_KE_GROUP)
            CardGroupCheckResult.DUI_INCLUDE_GROUP -> createEmptyDependentGroup(CardGroupCheckResult.SHUN_OR_KE_GROUP)
            CardGroupCheckResult.INVALID_GROUP -> {
                (1..9).mapNotNull {
                    if (checkIfDuiInclude(groupAddCard(it).cards)) {
                        it.toString()
                    } else null
                }.let { ChangeGroupTypeDependency(it, CardGroupCheckResult.DUI_INCLUDE_GROUP) }
            }
        }
    }

    fun changeToShunOrKeGroupDependency(): ChangeGroupTypeDependency {
        return when (checkResult) {
            CardGroupCheckResult.SHUN_OR_KE_GROUP -> createEmptyDependentGroup(CardGroupCheckResult.SHUN_OR_KE_GROUP)
            CardGroupCheckResult.DUI_INCLUDE_GROUP,
            CardGroupCheckResult.INVALID_GROUP -> {
                (1..9).mapNotNull {
                    if (checkIfShunOrKe(groupAddCard(it).cards)) {
                        it.toString()
                    } else null
                }.toList()
                    .let {
                        ChangeGroupTypeDependency(it, CardGroupCheckResult.SHUN_OR_KE_GROUP)
                    }
            }
        }
    }

    private fun groupAddCard(card: Int): CardGroup {
        return CardGroup(cards.toMutableList().let {
            it.add(card.toString())
            it.sorted()
        })
    }

    private fun checkGroup(): CardGroupCheckResult {
        return when {
            checkIfShunOrKe(cards) -> {
                CardGroupCheckResult.SHUN_OR_KE_GROUP
            }
            checkIfDuiInclude(cards) -> {
                CardGroupCheckResult.DUI_INCLUDE_GROUP
            }
            else -> CardGroupCheckResult.INVALID_GROUP
        }
    }


    /**
     * 检测是否符合顺或者刻
     */
    private fun checkIfShunOrKe(list: List<String>): Boolean {
        if (list.size % 3 != 0) return false

        val tempCards = list.toMutableList()
        (1..9).forEach { num ->
            while (tempCards.count { it == num.toString() } >= 3) {
                tempCards.remove(num.toString())
                tempCards.remove(num.toString())
                tempCards.remove(num.toString())
            }
        }

        (1..7).forEach { num ->
            while (tempCards.contains(num.toString()) &&
                tempCards.contains((num + 1).toString()) &&
                tempCards.contains((num + 2).toString())
            ) {
                tempCards.remove(num.toString())
                tempCards.remove((num + 1).toString())
                tempCards.remove((num + 2).toString())
            }
        }

        return tempCards.isEmpty()
    }

    private fun checkIfDuiInclude(list: List<String>): Boolean {
        if (list.size % 3 != 2) return false
        return (1..9).any { num ->
            val cards = list.toMutableList()
            if (cards.count { it == num.toString() } >= 2) {
                cards.remove(num.toString())
                cards.remove(num.toString())
                checkIfShunOrKe(cards)
            } else false
        }
    }
}

enum class CardGroupCheckResult {
    DUI_INCLUDE_GROUP,
    SHUN_OR_KE_GROUP,
    INVALID_GROUP,
}