package com.tangtang.practice1

import com.tangtang.practice1.MahjongPoint.Companion.parsePoint
import java.io.File

fun main(args: Array<String>) {
    val dataList = extractData("TestCase-1.txt")
    dataList.map { HandTile(it.handMahjongList) to it }.forEach(::info)
}

private fun info(pair: Pair<HandTile, TestCase1>) {
    val (handTile, testCase) = pair
    val readies = handTile.checkReady()
    if (readies.isEmpty()) {
        println("it is not ready for winning. (TestCase: $testCase)")
    } else {
        println("it is ready : expect = ${testCase.winningList}, actual = ${readies}. (TestCase: $testCase)")
    }
}

fun extractData(path: String): List<TestCase1> {
    val file = File(path)
    return file.readLines().filter { it.isNotBlank() }.map(::createTestCases)
}

fun createTestCases(txt: String): TestCase1 {
    val rawStr = txt.split(",")
    val handMahjongList = parseMahjong(rawStr.getOrNull(0) ?: "")
    val winningList = parseMahjong(rawStr.getOrNull(1) ?: "").toSet()
    return TestCase1(txt, handMahjongList, winningList)
}

private fun parseMahjong(mahjongString: String): List<Mahjong> {
    val zero = '0'.code
    return mahjongString.split("-").flatMapIndexed { index, str ->
        when (index) {
            0 -> str.map { Mahjong(parsePoint(it.code - zero), MahjongType.Character) }
            1 -> str.map { Mahjong(parsePoint(it.code - zero), MahjongType.Circle) }
            else -> str.map { Mahjong(parsePoint(it.code - zero), MahjongType.Bamboo) }
        }
    }
}

data class TestCase1(
    val rawString: String,
    val handMahjongList: List<Mahjong>,
    val winningList: Set<Mahjong>
)