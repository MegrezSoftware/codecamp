interface IMajiangSolution {
    fun execute(cardGroup: MajiangCardGroup): MajiangCardGroup
}

/**
 * 采用全排列的方式计算听牌，耗时较长
 */
class MajiangSolution : IMajiangSolution {
    override fun execute(cardGroup: MajiangCardGroup): MajiangCardGroup {
        val cardGroupWithoutFlower = cardGroup.excludePowerCardGroup()
        val additionalCount = cardGroup.extractPowerCardGroup().count()
        return computeTingGroup(cardGroupWithoutFlower, MajiangCardGroup.empty(), additionalCount + 1)
    }

    /**
     * 计算听牌组
     */
    private fun computeTingGroup(
        cardGroupWithoutFlower: MajiangCardGroup,
        dependentGroup: MajiangCardGroup,
        additionalCount: Int
    ): MajiangCardGroup {
        return if (additionalCount > 0) {
            val allCardsWithoutFlower = MajiangCardGroup.allCards().excludePowerCardGroup().cardItems
            val dependentCardItems = allCardsWithoutFlower.map {
                computeTingGroup(
                    cardGroupWithoutFlower = cardGroupWithoutFlower.addCardItem(it),
                    dependentGroup = dependentGroup.addCardItem(it),
                    additionalCount = additionalCount - 1,
                )
            }.flatMap { it.cardItems }
            MajiangCardGroup(dependentCardItems).removeDuplicated().sorted()
        } else {
            if (checkIfHupai(cardGroupWithoutFlower)) {
                dependentGroup
            } else {
                MajiangCardGroup.empty()
            }
        }
    }

    private fun checkIfHupai(group: MajiangCardGroup): Boolean {
        if (!group.isValid()) return false
        if (group.cardItems.size != 14) return false
        return reduce(group.sorted(), false)
    }

    private fun reduce(group: MajiangCardGroup, duiRemoved: Boolean): Boolean {
        if (group.cardItems.isEmpty()) return true
        return removeDui(group, duiRemoved) || removeShun(group, duiRemoved) || removeKe(group, duiRemoved)
    }

    private fun removeDui(group: MajiangCardGroup, duiRemoved: Boolean): Boolean {
        if (duiRemoved) return false
        val cardList = group.cardItems.toMutableList()
        val first = cardList.first()
        return if (cardList.count { it.value() == first.value() } >= 2) {
            cardList.remove(first)
            cardList.remove(cardList.first { it.value() == first.value() })
            reduce(MajiangCardGroup(cardList), true)
        } else false
    }

    private fun removeShun(group: MajiangCardGroup, duiRemoved: Boolean): Boolean {
        val cardList = group.cardItems.toMutableList()
        val first = cardList.first()
        val second = cardList.find { it.value() == first.value() + 1 }
        val third = cardList.find { it.value() == first.value() + 2 }
        return if (second != null && third != null) {
            cardList.remove(first)
            cardList.remove(second)
            cardList.remove(third)
            reduce(MajiangCardGroup(cardList), duiRemoved)
        } else false
    }

    private fun removeKe(group: MajiangCardGroup, duiRemoved: Boolean): Boolean {
        val cardList = group.cardItems.toMutableList()
        val first = cardList.first()
        return if (cardList.count { it.value() == first.value() } >= 3) {
            cardList.remove(first)
            cardList.remove(cardList.first { it.value() == first.value() })
            cardList.remove(cardList.first { it.value() == first.value() })
            reduce(MajiangCardGroup(cardList), duiRemoved)
        } else false
    }
}
