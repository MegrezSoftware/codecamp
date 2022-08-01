package com.practice.majiang.game

import com.practice.majiang.IPlayer
import com.practice.majiang.data.MaJiang

/**
 * 回合
 * @param host 本回合的玩家
 * @param input 本回合输入的牌
 */
class Round(
    val host: IPlayer,
    val players: List<IPlayer>,
    val input: MaJiang,
) {

    fun starRound(): RoundResult {
        val hostHu = host.checkHu(input)
        if (hostHu) {
            return RoundOver(listOf(host))
        }
        host.pick(input)
        val hostDiscard = host.discard()
        val huPlayers = players.filter {
            it.checkHu(hostDiscard)
        }
        if (huPlayers.isNotEmpty()) {
            return RoundOver(huPlayers)
        }

        val pengPlayer = players.find {
            it.checkPeng(hostDiscard)
        }

        if (pengPlayer != null) {
            return RoundContinue(nextHost = pengPlayer, discard = hostDiscard)
        }

        val hostIndex = players.indexOf(host)
        val nextHost = if (hostIndex < players.size - 1) {
            players[hostIndex + 1]
        } else {
            players[0]
        }
        return if (nextHost.checkChi(hostDiscard)) {
            RoundContinue(nextHost = nextHost, discard = hostDiscard)
        } else {
            RoundComplete(nextHost = nextHost)
        }

    }

}

sealed class RoundResult

class RoundOver(
    val winner: List<IPlayer>
) : RoundResult()

class RoundContinue(
    val nextHost: IPlayer, val discard: MaJiang
) : RoundResult()

class RoundComplete(val nextHost: IPlayer) : RoundResult()