package com.practice.majiang.game

import com.practice.majiang.IGame
import com.practice.majiang.IPlayer
import com.practice.majiang.PlayerNode

class MaJiangGame(private val dealer: IDealer) : IGame {

    override val players: Array<IPlayer>

    init {
        val player1 = Player()
        val player2 = Player()
        val player3 = Player()
        val player4 = Player()

        players = arrayOf(
            PlayerNode(player = player1, next = player2),
            PlayerNode(player = player2, next = player3),
            PlayerNode(player = player3, next = player4),
            PlayerNode(player = player4, next = player1)
        )
    }


    override fun start() {

        players.forEach {
            it.input(dealer.dealerMaJiang())
        }
        playerTerm(player = players[0])

    }

    private fun playerTerm(player: IPlayer) {

        if (player.checkHu(dealer.pickMaJiang())) {
            gameOver(winner = listOf(player))
        } else {
            val discard = player.discard()

            val otherPlayers = players.toMutableList().apply { remove(player) }.toList()
            val huPlayers = otherPlayers.filter {
                it.checkHu(discard)
            }
            if (huPlayers.isNotEmpty()) {
                gameOver(huPlayers)
                return
            }
            val pengPlayer = huPlayers.find { it.checkHu(discard) }
            pengPlayer?.pick(discard)

        }

    }

    private fun gameOver(winner: List<IPlayer>) {

    }

}