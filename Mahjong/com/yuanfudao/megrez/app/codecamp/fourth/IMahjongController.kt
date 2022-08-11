package com.yuanfudao.megrez.app.codecamp.fourth
interface IMahjongController {
    fun startGame()
}

interface IEachTurnCarer {
    fun currentPlayer(): OneTurnState
    fun nextTurn()
    fun skipToPlayersNext(skipPlayerId: Long)
}


interface IWinChecker {
    fun isWin(): Boolean
}

interface IWaitingCardJudge {
    fun isWaitingForCard()
    fun getWaitingCardList(): List<MahjongCard>
}

interface IDistanceCalculator {
    fun compareMostUselessCardToRemove(newCardComing: MahjongCard): MahjongCard
    fun isBecomeMatchShortenDistance(discardCard: MahjongCard): Boolean
    fun getMostUselessCard(): MahjongCard
}

