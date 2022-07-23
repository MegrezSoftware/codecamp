/**
 * 听牌组
 */
data class ChangeGroupTypeDependency(
    override val dependencies: List<String>,
    val resultType: CardGroupCheckResult,
) : ICardGroupDependency {
}

interface ICardGroupDependency {
    val dependencies: List<String>
}

data class CardGroupDependency(override val dependencies: List<String>) : ICardGroupDependency

fun ICardGroupDependency.merge(groupDependency: ICardGroupDependency): ICardGroupDependency {
    return CardGroupDependency(
        listOf(dependencies, groupDependency.dependencies)
            .flatten()
            .toSet().toList()
    )
}

/**
 * 所以听牌的输出
 */
data class DependentCard(
    val groupDependencies: List<ICardGroupDependency>
) {
    fun genResultString(): String {
        return groupDependencies
            .map { genCardGroupResult(it) }
            .toList()
            .joinToString(separator = "-") { it }
    }

    private fun genCardGroupResult(cardGroup: ICardGroupDependency): String {
        val list: List<String> = cardGroup.dependencies
        return list.joinToString(separator = "") { it }
    }
}