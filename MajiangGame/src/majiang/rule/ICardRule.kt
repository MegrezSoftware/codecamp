package majiang.rule

import majiang.card.HandCardGroup
import majiang.card.ICardPool

/**
 * 麻将规则
 */
interface ICardRule {
    /**
     * 生成牌堆，根据实现来决定生成的牌中有没有花牌、字牌，每种牌的数量等
     */
    fun createCardPool(): ICardPool

    /**
     * 生成初始牌，根据实现决定是13牌还是16牌
     */
    fun initHandCardGroup(cardPool: ICardPool): HandCardGroup
}