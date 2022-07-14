package com.yuanfudao.megrez.app.codecamp.first

import java.util.*

/**
 * Created by lei.jialin on 2022/7/13
 */
class GroupArrangement {
    fun generatePossiblePair(input: List<Mahjong>) {
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
        Logg.debugLn("Case input: $input")
        val resCircle = MatchResolver(circle).resolveSameGroup()
        val resLine = MatchResolver(line).resolveSameGroup()
        val resCharacter = MatchResolver(character).resolveSameGroup()

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
            val result = WhichToWin(
                circle = circleChoice,
                line = lineChoice,
                character = characterChoice,
            ).getWhichToWinList()

            LinkedList<List<Match>>().apply {
                circleChoice?.match?.let { add(it) }
                lineChoice?.match?.let { add(it) }
                characterChoice?.match?.let { add(it) }
                println("可能组合 ${case++}: $this")
            }
        }

    }

}