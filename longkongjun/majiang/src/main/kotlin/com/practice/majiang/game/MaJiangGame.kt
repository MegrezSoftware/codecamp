package com.practice.majiang.game

import com.practice.majiang.IGame
import com.practice.majiang.IPlayer
import com.practice.majiang.data.MaJiang

class MaJiangGame(
    private val players: List<IPlayer>,
) : IGame {

    override fun start(dealer: IDealer) {
        players.forEach {
            it.input(dealer.dealerMaJiang())
        }
        var round = nextRound(host = players.random(), dealer = dealer)
        while (!dealer.pickOut()) {
            round = when (val roundResult = round.starRound()) {
                is RoundComplete -> {
                    nextRound(host = roundResult.nextHost, dealer = dealer)
                }

                is RoundContinue -> {
                    nextRound(host = roundResult.nextHost, input = roundResult.discard)
                }

                is RoundOver -> {
                    gameOver(roundResult.winner)
                    return
                }
            }
        }
        gameOverWithNoWinner()
    }

    private fun nextRound(host: IPlayer, dealer: IDealer): Round {
        return Round(host = host, players = players.toList(), input = dealer.pickMaJiang())
    }

    private fun nextRound(host: IPlayer, input: MaJiang): Round {
        return Round(host = host, players = players.toList(), input = input)
    }


    private fun gameOver(winner: List<IPlayer>) {

    }

    private fun gameOverWithNoWinner() {

    }

}