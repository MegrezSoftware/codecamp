package majiang.process

import majiang.card.HandCardGroup
import majiang.card.MajiangCard
import majiang.player.IPlayer

/**
 * 麻将摸牌行为
 */
interface IMajiangReceiveCardAction {
    fun onReceiveCard(card: MajiangCard, player: IPlayer): IReceiveCardActionResult
}

/**
 * kotlin版本较低，这里应该使用sealed interface
 */
interface IReceiveCardActionResult {
    val handCardGroup: HandCardGroup
}

/**
 * 胡牌
 */
data class ReceiveWinResult(
    override val handCardGroup: HandCardGroup,
) : IReceiveCardActionResult

/**
 * 弃牌
 */
data class ReceiveAbandonCardResult(
    override val handCardGroup: HandCardGroup,
    val abandonCard: MajiangCard,
) : IReceiveCardActionResult

/**
 * 杠
 */
data class ReceiveGangCardResult(
    override val handCardGroup: HandCardGroup,
) : IReceiveCardActionResult

