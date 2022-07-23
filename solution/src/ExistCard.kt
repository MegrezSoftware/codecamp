/**
 * 所有牌的输入
 */
data class ExistCard(
    val cardGroups: List<CardGroup>
) {
    fun findSolution(): DependentCard {
        return cardGroups.map { group ->
            //获取其他group的检查结果
            val otherCheckResults = cardGroups.toMutableList().also {
                it.remove(group)
            }.map { it.checkResult }

            //获取当前组变化的依赖
            computeGroupChangeDependency(group, otherCheckResults)
        }.let { DependentCard(it) }
    }

    /**
     * 计算当前组获牌变化后的状态与其他组是否满足胡牌
     * @param group 当前组
     * @param otherDependentGroupTypeDependency 其他组的状态
     */
    private fun computeGroupChangeDependency(
        group: CardGroup,
        otherDependentGroupTypeDependency: List<CardGroupCheckResult>
    ): ICardGroupDependency {
        val shunOrKeDependent = otherDependentGroupTypeDependency.toMutableList().let {
            val dependentGroup = group.changeToShunOrKeGroupDependency()
            it.add(dependentGroup.resultType)
            if (checkCardSuccess(it)) {
                dependentGroup
            } else createEmptyDependentGroup(CardGroupCheckResult.SHUN_OR_KE_GROUP)
        }
        val duiDependency = otherDependentGroupTypeDependency.toMutableList().let {
            val dependentGroup = group.changeToDuiGroupDependency()
            it.add(dependentGroup.resultType)
            if (checkCardSuccess(it)) {
                dependentGroup
            } else createEmptyDependentGroup(CardGroupCheckResult.DUI_INCLUDE_GROUP)

        }
        return shunOrKeDependent.merge(duiDependency)
    }

    /**
     * 判断所有的依赖胡牌组
     */
    private fun checkCardSuccess(groupTypeDependencyResult: List<CardGroupCheckResult>): Boolean {
        return groupTypeDependencyResult.filterNot { it == CardGroupCheckResult.SHUN_OR_KE_GROUP }.let {
            it.size == 1 && it.first() == CardGroupCheckResult.DUI_INCLUDE_GROUP
        }
    }
}