package com.yuanfudao.megrez.app.codecamp.first

import java.util.ArrayList

/**
 * Created by lei.jialin on 2022/7/13
 */
class WinChecker {
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
        Logg.debugLn("Case input: $input")
        val resCircle = MatchResolver(circle).calcOnSameGroup()
        val resLine = MatchResolver(line).calcOnSameGroup()
        val resCharacter = MatchResolver(character).calcOnSameGroup()

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
            val circleChoice = resCircle.getOrNull(combine[0]) ?: emptyList()
            val lineChoice = resLine.getOrNull(combine[1]) ?: emptyList()
            val characterChoice = resCharacter.getOrNull(combine[2]) ?: emptyList()
//            val result = isOnlyMissingOne(
//                circle = circleChoice,
//                line = lineChoice,
//                character = characterChoice,
//            )

            println("结果${case++}: ${circleChoice + lineChoice + characterChoice}")
        }

    }
}