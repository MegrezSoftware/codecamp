package com.yuanfudao.megrez.app.codecamp.first

import java.util.*

class MahjongGame {
    fun checkWin(input: List<Mahjong>) {
        val circle = ArrayList<Circle>()
        val line = ArrayList<Line>()
        val character = ArrayList<Character>()
        input.forEach { card ->
            when (card) {
                is Circle -> circle.add(card)
                is Line -> line.add(card)
                is Character -> character.add(card)
            }
        }
        circle.sort()
        line.sort()
        character.sort()
        Logg.debugLn("Case input: $input")
        Logg.debugLn("Case input: ${circle + line + character}")
        val resCircle = keepStuffingBack(circle)
        val resLine = keepStuffingBack(line)
        val resCharacter = keepStuffingBack(character)

        // combination on three type
        // only one pair will be allowed
        var case = 1
        val combination =
            TakeIndexCombination().permute(listOf(resCircle.size, resLine.size, resCharacter.size))
        for (i in combination.indices) {
            val combine = combination[i]
            if (combination.size < 3) {
                Logg.debugLn("错误的组合下标结果")
                return
            }
            val circleChoice = resCircle.getOrNull(combine[0])
            val lineChoice = resLine.getOrNull(combine[1])
            val characterChoice = resCharacter.getOrNull(combine[2])
            val result = isOnlyMissingOne(
                circle = circleChoice,
                line = lineChoice,
                character = characterChoice,
            )

            println("结果${case++}: ${result.fullMatch}, 等待听牌list: ${result.waitingMissingList}, 对儿：${result.pairList}，whichToWin: ${result.whichToWin}")
        }

    }

    private fun keepStuffingBack(sorted: List<Mahjong>): List<CompetitorStatus> {
        if (sorted.isEmpty()) return LinkedList()
        val matchMap = HashMap<String, LinkedList<Match>>()
        indexMatchMap(sorted, matchMap)
        return findCandidateInBetween(sorted, matchMap)
    }

    private fun findCandidateInBetween(
        sorted: List<Mahjong>,
        matchMap: java.util.HashMap<String, LinkedList<Match>>
    ): List<CompetitorStatus> {
        val candidate = HashSet<CompetitorStatus>()
        val belongName = sorted.firstOrNull()?.shortName
        Logg.debugLn("$belongName 部分牌输入list: $sorted")
        for (i in sorted.indices) {
            // i ~ end
            val suffix = matchMap["$i-${sorted.size - 1}"]
            //  0 ~ i - 1
            val prefix = matchMap["0-${i - 1}"]

            Logg.debugLn("$belongName 分牌第 ${candidate.size + 1}种：从 pos=$i, element=${sorted[i]} private fun ，切割前面的:${matchMap["0-${i - 1}"]}，前面：$prefix, 后面: $suffix")
            val possible = LinkedList<Match>()
            when {
                prefix?.isNotEmpty() == true && suffix?.isNotEmpty() == true -> {
                    // If no merge procedure happen between suffix and prefix
                    if (possible.isEmpty()) {
                        possible.addAll(prefix)
                        possible.addAll(suffix)
                    }
                }
                prefix?.isNotEmpty() == true -> {
                    possible.addAll(prefix)

                }
                suffix?.isNotEmpty() == true -> {
                    possible.addAll(suffix)
                }
                else -> continue
            }
            val competitor = possible.judgeStatus()
            if (competitor.isOneStepMoreToComplete()) {
                Logg.debugLn("$belongName 部分有听牌：听牌 = ${competitor.missing}, 手牌排列=$possible")
            } else {
                Logg.debugLn("$belongName 部分无听牌：手牌排列=$possible")
            }
            candidate.add(competitor)
        }
        return candidate.toList()
    }

    private fun indexMatchMap(
        sorted: List<Mahjong>,
        matchMap: java.util.HashMap<String, LinkedList<Match>>
    ) {
        sorted.forEachIndexed { index, mahjong ->
            val self = Match.SingleOne(Card(mahjong = mahjong))
            matchMap["$index-$index"] = LinkedList<Match>().apply { offerLast(self) }
        }
        for (start in sorted.indices) {
            for (cursor in (start + 1 until sorted.size)) {
                val headOfRow = sorted[start]
                val lastTurn = matchMap.getOrDefault("$start-${cursor - 1}", LinkedList<Match>())
                val lastTopElement = lastTurn.peekLast() ?: Match.None(cursor - 1)
                val replaceCellStart = cursor - lastTopElement.length
                val myTurn = matchMap.getOrPut("$start-$cursor") { LinkedList<Match>(lastTurn) }
                if (lastTopElement.length == 3 || replaceCellStart !in sorted.indices) {
                    myTurn.offerLast(
                        Match.SingleOne(
                            Card(
                                mahjong = sorted.get(cursor)
                            )
                        )
                    )
                    Logg.debugLn("([$start, $cursor]: headOfRow: $headOfRow) 单独的一个 last:$lastTopElement), 结果list: $myTurn")
                } else {
                    val cell = MatchMaker.makeMatch(sorted.subList(replaceCellStart, cursor + 1))
                    if (cell !is Match.None && cell.level > lastTopElement.level) {
                        myTurn.pollLast()
                        myTurn.offerLast(cell)
                        Logg.debugLn("([$start, $cursor]: headOfRow: $headOfRow) 合并前一项。扔掉last:$lastTopElement, 合并新项:last+${sorted[cursor]}=cell$cell, 结果list: $myTurn")
                    } else {
                        myTurn.offerLast(
                            Match.SingleOne(
                                Card(
                                    mahjong = sorted.get(cursor)
                                )
                            )
                        )
                        Logg.debugLn("([$start, $cursor]: headOfRow: $headOfRow) 无法合并前一项。新项不合法: last+${sorted[cursor]}=cell$cell，保持last:$lastTopElement, 结果list: $myTurn")
                    }
                }
            }
        }
    }

    private fun isOnlyMissingOne(
        line: CompetitorStatus?,
        circle: CompetitorStatus?,
        character: CompetitorStatus?
    ): PlayCheckWinResult {
        val list = listOfNotNull(line, circle, character)
        val missing = list.flatMap { it.missing }
        val pair = list.flatMap { it.pair }
        val whichToWin = if (missing.size == 1 && pair.size == 1) missing.first()
        else null
        return PlayCheckWinResult(
            whichToWin = whichToWin,
            fullMatch = list.flatMap { it.matchs },
            waitingMissingList = missing,
            pairList = pair,
            doubleCount = list.sumOf { it.doubles }
        )
    }

    data class PlayCheckWinResult(
        val whichToWin: Match?,
        val fullMatch: List<Match>,
        val waitingMissingList: List<Match>,
        val pairList: List<Match>,
        val doubleCount: Int,
    )

    private fun CompetitorStatus.isOneStepMoreToComplete(): Boolean {
        return missing.size <= 1
    }

    private fun List<Match>.judgeStatus(): CompetitorStatus {
        val matchs = this
        var twoCount = 0
        var cardCount = 0
        val missing = ArrayList<Match>()
        val pair = ArrayList<Match>()
        matchs.forEach { match ->
            if (match is Match.Doubles) pair.add(match)
            else if (match.isMissing()) {
                missing.add(match)
            }
            when (match.length) {
                2 -> {
                    twoCount++
                }
            }
            cardCount += match.length
        }
        return CompetitorStatus(missing, pair, twoCount, cardCount, matchs)
    }

}