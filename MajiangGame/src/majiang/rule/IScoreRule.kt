package majiang.rule

import majiang.card.MajiangCard
import majiang.score.IScore

/**
 * 计分规则
 */
interface IScoreRule {
    /**
     * 计分手牌分数
     */
    fun computeCardGroupScore(cards: List<MajiangCard>): IScore
}