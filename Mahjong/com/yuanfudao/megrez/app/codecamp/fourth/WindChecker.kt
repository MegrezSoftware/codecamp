package com.yuanfudao.megrez.app.codecamp.fourth

class WindChecker(private val newPlayer: MahjongPlayer) : IWinChecker {
    override fun isWin(): Boolean = newPlayer.doubleCount == 1 && newPlayer.threeCount == 3
}