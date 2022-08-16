package majiang.player

import majiang.card.HandCardGroup
import majiang.card.MajiangCard
import majiang.process.IMajiangListenCardAction
import majiang.process.IMajiangListenCardResult
import majiang.process.IReceiveCardActionResult

/**
 * 玩家直接操作的接口，接口方法为玩家可以操作的集合
 */
interface IPlayer {
    /**
     * 获取初始手牌
     * @param handCardGroup 手牌组
     */
    fun onReceiveHandCards(handCardGroup: HandCardGroup)

    /**
     * 获取当前手牌
     */
    fun currentHandCardGroup(): HandCardGroup

    fun onReceiveCard(card: MajiangCard): IReceiveCardActionResult

    fun onListenCard(card: MajiangCard): IMajiangListenCardResult
}

/**
 * 玩家端
 */
class PlayerClient : IPlayer {
    override fun onReceiveHandCards(handCardGroup: HandCardGroup) {
        TODO("Not yet implemented")
    }

    override fun currentHandCardGroup(): HandCardGroup {
        TODO("Not yet implemented")
    }

    override fun onReceiveCard(card: MajiangCard): IReceiveCardActionResult {
        TODO("Not yet implemented")
    }

    override fun onListenCard(card: MajiangCard): IMajiangListenCardResult {
        TODO("Not yet implemented")
    }

}

/**
 * 玩家端代理，通过与玩家端通信，进而代理麻将玩家端参与麻将服务器的操作。
 */
class PlayerProxy : IPlayer {
    override fun onReceiveHandCards(handCardGroup: HandCardGroup) {
        TODO("Not yet implemented")
    }

    override fun currentHandCardGroup(): HandCardGroup {
        TODO("Not yet implemented")
    }

    override fun onReceiveCard(card: MajiangCard): IReceiveCardActionResult {
        TODO("Not yet implemented")
    }

    override fun onListenCard(card: MajiangCard): IMajiangListenCardResult {
        TODO("Not yet implemented")
    }

}