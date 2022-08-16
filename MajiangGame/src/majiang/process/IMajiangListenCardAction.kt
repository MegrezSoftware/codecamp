package majiang.process

import majiang.card.HandCardGroup
import majiang.card.MajiangCard
import majiang.player.IPlayer

/**
 * 对其他玩家打牌的行为
 */
interface IMajiangListenCardAction {
    fun onListenCard(card: MajiangCard, player: IPlayer): IMajiangListenCardResult
}

/**
 * kotlin版本较低，这里应该使用sealed interface
 */
interface IMajiangListenCardResult {
    val handCardGroup: HandCardGroup
}

data class ListenWinResult(override val handCardGroup: HandCardGroup) : IMajiangListenCardResult
data class ListenPengResult(
    override val handCardGroup: HandCardGroup,
    val abandonCard: MajiangCard
) : IMajiangListenCardResult

data class ListenChiResult(override val handCardGroup: HandCardGroup) : IMajiangListenCardResult
data class ListenGangResult(override val handCardGroup: HandCardGroup) : IMajiangListenCardResult
data class ListenAbandonResult(override val handCardGroup: HandCardGroup) : IMajiangListenCardResult
