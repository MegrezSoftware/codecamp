package majiang.process

import majiang.card.ICardPool
import majiang.card.MajiangCard
import majiang.player.IPlayer
import majiang.player.PlayerClient
import majiang.player.PlayerProxy
import majiang.rule.ICardRule
import majiang.rule.IScoreRule
import majiang.score.IScore

/**
 * 开始整个麻将游戏
 * C/S架构，MajiangGame[MajiangGame]作为一个server，为每个player[IPlayer]创建proxy[PlayerProxy]，
 * proxy与各个client[PlayerClient]进行通信。
 */
interface IMajiangGame {
    val cardRule: ICardRule
    val scoreRule: IScoreRule

    /**
     * Player代理
     */
    val players: List<IPlayer>

    fun nextPlayer(): IPlayer

    /**
     * 发牌
     */
    fun dealCards()

    /**
     * 玩一回合，从一个人摸牌到下一个人摸牌定义为一回合
     * @return true 游戏结束，false 游戏未结束
     */
    fun playingOneRound(): Boolean

    /**
     * 计分
     */
    fun computeScore(): List<Pair<IPlayer, IScore>>
}

class MajiangGame(
    override val cardRule: ICardRule,
    override val scoreRule: IScoreRule,
    override val players: List<IPlayer>
) : IMajiangGame {
    private val mCardPool: ICardPool by lazy {
        cardRule.createCardPool()
    }
    private var mCurrentPlayer = players.first()

    override fun dealCards() {
        for (player in players) {
            player.onReceiveHandCards(cardRule.initHandCardGroup(mCardPool))
        }
    }

    override fun playingOneRound(): Boolean {
        return receiveCard(nextPlayer())
    }

    private fun receiveCard(player: IPlayer): Boolean {
        if (!mCardPool.hasCard()) {
            //todo 流局
            return true
        }
        val card = mCardPool.requestCard()
        val actionResult: IReceiveCardActionResult = player.onReceiveCard(card)
        return if (actionResult is ReceiveWinResult) {
            //todo currentPlayer赢了，进入后续阶段
            return true
        } else if (actionResult is ReceiveGangCardResult) {
            //杠牌，递归摸牌
            return receiveCard(player)
        } else {
            //弃牌，下家操作
            listenCard(card, player)
        }
    }

    private fun listenCard(card: MajiangCard, abandonCardPlayer: IPlayer): Boolean {
        val player = nextPlayer()
        return if (player == abandonCardPlayer) {
            //无人响应打出去的牌，结束
            false
        } else {
            when (val result = player.onListenCard(card)) {
                is ListenWinResult -> {
                    true
                }
                is ListenPengResult -> {
                    //碰牌操作
                    listenCard(result.abandonCard, player)
                }
                is ListenChiResult -> {
                    //吃牌操作
                    TODO()
                }
                is ListenGangResult -> {
                    //杠牌操作
                    receiveCard(player)
                }
                else -> {
                    //当前玩家弃牌
                    listenCard(card, abandonCardPlayer)
                }
            }
        }
    }

    override fun computeScore(): List<Pair<IPlayer, IScore>> {
        TODO("计分")
    }

    override fun nextPlayer(): IPlayer {
        TODO("获取当前的player")
    }

}
