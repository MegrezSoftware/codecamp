package majiang.card

/**
 * 麻将手牌
 * @property remainCards 剩余手牌
 * @property pengList "碰"的牌组
 * @property gangList "杠"的牌组
 */
data class HandCardGroup(
    val remainCards: List<MajiangCard>,
    val pengList: List<List<MajiangCard>>,
    val gangList: List<List<MajiangCard>>
)