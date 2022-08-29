package com.yuanfudao.megrez.app.codecamp.fourth

data class OneTurnState(
    val lastTurnPlayerIndex: Int,
    val totalRemainCardCount: Int,
    val playedTurnCount: Int,
    val playerList: List<MahjongPlayer>,
    val alreadyWinPlayer: List<MahjongPlayer>,
)

data class MahjongPlayer(
    val playerIndex: Int,
    val countMap: Map<Int, Int>,
    val card: List<MahjongCard>,
    val user: User,
    val threeCount: Int,
    val doubleCount: Int,
) {
}


data class User(
    val displayName: String,
    val playerId: Long,
)

