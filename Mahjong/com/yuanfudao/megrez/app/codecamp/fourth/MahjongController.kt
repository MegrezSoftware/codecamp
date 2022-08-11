package com.yuanfudao.megrez.app.codecamp.fourth

class MahjongController(
    private val playerList: List<MahjongPlayer>,
    private val totalRemainCardCount: Int
) : IMahjongController {

    override fun startGame() {
        val finalWinner = nextTurnGame(
            indexToCheckThisTurn = 0,
            envriomentOfThisTurn = OneTurnState(
                lastTurnPlayerIndex = 0,
                totalRemainCardCount = totalRemainCardCount,
                playedTurnCount = 0,
                alreadyWinPlayer = emptyList(),
                playerList = playerList,
            )
        )
        if (finalWinner != null) {
            // 有人获胜
        } else {
            // 牌用完了也没有人获胜
        }
    }

    private fun nextTurnGame(
        indexToCheckThisTurn: Int,
        envriomentOfThisTurn: OneTurnState,
    ): MahjongPlayer? {
        if (envriomentOfThisTurn.totalRemainCardCount <= 0) {
            // 牌已用尽或有人获胜了
            return envriomentOfThisTurn.alreadyWinPlayer.firstOrNull()
        }
        // 随机摸牌
        val newCardComing: MahjongCard = randomlyTakeOutWildCard()

        val currentPlayer = playerList[indexToCheckThisTurn]

        // 计算距离获胜最远的牌
        val distanceCalculator = DistanceCalculator(currentPlayer.card)

        // 摸牌后弃牌
        val discardCard = distanceCalculator.compareMostUselessCardToRemove(newCardComing)

        val newList = if (discardCard != newCardComing) {
            // 本轮摸起的牌是需要的，另外抛弃了一张牌
            // 更新手牌
            playerList.toMutableList().apply {
                val newCardsInHand =
                    currentPlayer.card.toMutableList().apply { this.remove(discardCard) }
                set(indexToCheckThisTurn, currentPlayer.copy(card = newCardsInHand))
            }
        } else {
            // 摸牌就是弃牌
            playerList
        }

        // 处理弃牌阶段，直到没有人再需要新抛弃的弃牌
        val skipUntilStopEat = anyoneNeedDiscardCard(
            iteratorPlayIndex = (indexToCheckThisTurn + 1) % playerList.size,
            discardCard = discardCard,
            foreachStep = 0,
            environmentThisTurn = OneTurnState(
                lastTurnPlayerIndex = indexToCheckThisTurn,
                playerList = newList,
                totalRemainCardCount = envriomentOfThisTurn.totalRemainCardCount - 1,
                playedTurnCount = envriomentOfThisTurn.playedTurnCount + 1,
                alreadyWinPlayer = envriomentOfThisTurn.alreadyWinPlayer,
            )
        )
        // 没有人吃弃牌，从skipUser开始下一轮
        return nextTurnGame(
            indexToCheckThisTurn = (skipUntilStopEat.lastTurnPlayerIndex + 1) % playerList.size,
            envriomentOfThisTurn = skipUntilStopEat,
        )
    }

    private fun randomlyTakeOutWildCard(): MahjongCard {
        TODO("Not yet implemented")
    }

    private fun anyoneNeedDiscardCard(
        iteratorPlayIndex: Int,
        discardCard: MahjongCard,
        foreachStep: Int,
        environmentThisTurn: OneTurnState,
    ): OneTurnState {
        // 遍历了3位玩家都不需要这个弃牌，直接下一轮
        if (foreachStep >= playerList.size - 1) {
            return environmentThisTurn
        }

        // 检查该玩家是否需要吃这张弃牌
        val mayNeedPlayer = playerList.get(iteratorPlayIndex)
        val distanceCalculator = DistanceCalculator(mayNeedPlayer.card)
        // 吃弃牌后赢面更大，则吃下
        val needEat = distanceCalculator.isBecomeMatchShortenDistance(discardCard)
        if (needEat) {
            // 吃下后需要打出新的弃牌
            val newTossOut = distanceCalculator.getMostUselessCard()
            val newPlayer = mayNeedPlayer.run {
                // 吃下后更新countMap信息
                val newCountMap = HashMap(countMap).also { map ->
                    map[discardCard.innerPresentIndex] =
                        map.getOrDefault(discardCard.innerPresentIndex, 0) + 1
                }
                // 吃下后更新手牌
                val newCardList = if (newTossOut != discardCard) {
                    card.toMutableList().also { cards ->
                        cards.remove(newTossOut)
                        cards.add(discardCard)
                    }
                } else card
                copy(countMap = newCountMap, threeCount = threeCount + 1, card = newCardList)
            }
            val newPlayerList =
                playerList.toMutableList().apply { set(iteratorPlayIndex, newPlayer) }
            val windChecker: IWinChecker = WindChecker(newPlayer)
            // 检查吃牌后是否直接赢了
            return if (windChecker.isWin()) {
                // 赢了直接结束游戏 totalRemainCardCount = 0
                OneTurnState(
                    lastTurnPlayerIndex = iteratorPlayIndex,
                    totalRemainCardCount = 0,
                    playedTurnCount = environmentThisTurn.playedTurnCount + 1,
                    alreadyWinPlayer = environmentThisTurn.alreadyWinPlayer + newPlayer,
                    playerList = newPlayerList,
                )
            } else {
                // 没赢就看哪位玩家需要吃新的弃牌
                anyoneNeedDiscardCard(
                    iteratorPlayIndex = (iteratorPlayIndex + 1) % playerList.size,
                    discardCard = newTossOut,
                    foreachStep = 0,
                    environmentThisTurn = environmentThisTurn.copy(
                        lastTurnPlayerIndex = iteratorPlayIndex,
                        playerList = newPlayerList,
                    ),
                )
            }
        } else {
            // 吃牌后赢面更小，或吃不了牌，询问下一位玩家是否需要吃这张弃牌
            return anyoneNeedDiscardCard(
                foreachStep = foreachStep + 1,
                iteratorPlayIndex = (iteratorPlayIndex + 1) % playerList.size,
                discardCard = discardCard,
                environmentThisTurn = environmentThisTurn,
            )
        }
    }

}
