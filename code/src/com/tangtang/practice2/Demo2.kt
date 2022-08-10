package com.tangtang.practice2

import com.tangtang.practice1.*
import java.io.File

fun main(args: Array<String>) {
    val dataList = extractData("TestCase-2.txt")
    dataList.map { HandTileWithPower(it.handMahjongList) to it }.forEach(::info)
}

private fun info(pair: Pair<HandTileWithPower, TestCase2>) {
    val (handTile, testCase) = pair
    val readies = handTile.checkReady()
    val isReady = (testCase.isAllWinning && readies.size == 27) || readies.isNotEmpty()
    val readyText = if (isReady) {
        " ready : "
    } else {
        " not ready for winning. "
    }
    val expectText = if (testCase.isAllWinning) {
        "all"
    } else {
        "${testCase.winningSet}"
    }
    println("it is$readyText expect = ${expectText}, actual = ${readies}. (TestCase: $testCase)")
}

fun extractData(path: String): List<TestCase2> {
    val file = File(path)
    return file.readLines().filter { it.isNotBlank() }.map(::createTestCases)
}

fun createTestCases(txt: String): TestCase2 {
    val rawStr = txt.split(",")
    val handMahjongString = rawStr.getOrNull(0) ?: ""
    val handTileList = parseMahjong(handMahjongString)

    val winString = rawStr.getOrNull(1) ?: ""
    val type = winString.split("-")
    var isAllWinning = false
    val winningSet = mutableSetOf<Mahjong>()
    when (type.size) {
        1 -> {
            when (type[0]) {
                "all" -> {
                    isAllWinning = true
                }
                "null" -> {
                }
                else -> {
                    winningSet.addAll(parseMahjong(winString))
                }
            }
        }
        else -> {
            winningSet.addAll(parseMahjong(winString))
        }
    }
    return TestCase2(txt, handTileList, winningSet, isAllWinning)
}

private fun parseMahjong(mahjongString: String): List<Mahjong> {
    val zero = '0'.code
    return mahjongString.split("-").flatMapIndexed { index, str ->
        when (index) {
            0 -> str.map { Mahjong(MahjongPoint.parsePoint(it.code - zero), MahjongType.Character) }
            1 -> str.map { Mahjong(MahjongPoint.parsePoint(it.code - zero), MahjongType.Circle) }
            2 -> str.map { Mahjong(MahjongPoint.parsePoint(it.code - zero), MahjongType.Bamboo) }
            else -> emptyList()
        }
    }
}

data class TestCase2(
    val rawString: String,
    val handMahjongList: List<Mahjong>,
    val winningSet: Set<Mahjong>,
    val isAllWinning: Boolean
)