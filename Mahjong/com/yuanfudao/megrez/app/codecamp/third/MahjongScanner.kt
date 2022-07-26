package com.yuanfudao.megrez.app.codecamp.third

import java.io.FileReader
import java.util.*
import kotlin.collections.ArrayList

object MahjongScanner {
    fun testCaseToAnswer(fileName: String) {
        val inputLines = ArrayList<InputMahjong>()
        var index = 0
        Scanner(FileReader(fileName)).use { sc ->
            while (sc.hasNextLine()) {  //按行读取字符串
                val line = sc.nextLine()
                line.split(",").run {
                    inputLines.add(
                        InputMahjong(
                            originInput = line,
                            caseIndex = index,
                            inputCardStr = this[0],
                            correctStr = this[1],
                        )
                    )
                }
                index++
            }
        }
        inputLines.forEach { case ->
            val waitingCardStr = divideGroupAndCheckWaiting(case.inputCardStr)
            val isCorrect = if (case.correctStr == waitingCardStr) "正确" else "错误"
            println("Case ${case.caseIndex}: 比对结果 = [$isCorrect]。麻将输入[${case.originInput}], 听牌列表：${waitingCardStr}")
        }
    }

    private fun divideGroupAndCheckWaiting(input: String): String {
        val inputList = input.split("-")
        val wans = inputList.parseIntList(0)
            .mapNotNull { each -> Wan.values().find { it.num == each }?.toInnerIndex() }
        val circle = inputList.parseIntList(1)
            .mapNotNull { each -> Circle.values().find { it.num == each }?.toInnerIndex() }
        val lines = inputList.parseIntList(2)
            .mapNotNull { each -> Line.values().find { it.num == each }?.toInnerIndex() }
        val chars = inputList.parseIntList(3)
            .mapNotNull { each -> Chars.values().find { it.num == each }?.toInnerIndex() }
        val arbitraryCount = inputList.parseIntList(4).size
        val constantMahjongList = wans + circle + lines + chars
        val waitingCard =
            MahjongWaitingFinder.whichToWinMahjong(constantMahjongList, arbitraryCount)
        val resultString = convertWaitingCardToString(waitingCard)
        return resultString
    }

    private fun convertWaitingCardToString(waitingCard: List<Mahjong>): String {
        val circles = ArrayList<Circle>()
        val wans = ArrayList<Wan>()
        val lines = ArrayList<Line>()
        val charsList = ArrayList<Chars>()
        waitingCard.forEach {
            when (it) {
                is Circle -> {
                    circles.add(it)
                }
                is Wan -> {
                    wans.add(it)
                }
                is Line -> {
                    lines.add(it)
                }
                is Chars -> {
                    charsList.add(it)
                }
            }
        }
        if (wans.size == Wan.values().size
            && circles.size == Circle.values().size
            && lines.size == Line.values().size
            && charsList.size == Chars.values().size
        ) {
            return "all"
        }
        val resultNumberList =
            listOf(wans.joinToNumber(), circles.joinToNumber(), lines.joinToNumber())

        return if (resultNumberList.all { it.isEmpty() }) "null"
        else {
            var join = resultNumberList.joinToString("-")
            while (join.endsWith("-"))
                join = join.removeSuffix("-")
            join
        }
    }

    private fun List<Mahjong>.joinToNumber(): String {
        return this.joinToString(
            separator = "",
            transform = { it.num.toString() })
    }

    private fun List<String>.parseIntList(index: Int): List<Int> {
        val inputList = this
        return (inputList.getOrNull(index) ?: "").split("").filter { it.isNotBlank() }
            .map { it.toInt() }
    }

    data class InputMahjong(
        val originInput: String,
        val caseIndex: Int,
        val inputCardStr: String,
        val correctStr: String
    )
}