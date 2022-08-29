package com.tangtang.practice1

import java.io.File
import java.io.FileInputStream


class HandTile(private val data: List<Mahjong>) {

    fun checkReady(): List<Mahjong> = Mahjong.allNormalMahjongCases.filter { isWinning(data + it) }

    private fun isWinning(mahjongList: List<Mahjong>): Boolean = mahjongList.map2SortedList().let {
        if (it.sum() > 14 || it.any { c -> c > 4 }) {
            false
        } else {
            checkWinning(it, 4, 1)
        }
    }

    private fun checkWinning(intList: List<Int>, straightOrSet: Int, pair: Int): Boolean {
        val countList = intList.dropWhile { it == 0 }

        if (straightOrSet < 0) return false
        if (pair < 0) return false
        if (countList.any { it < 0 }) return false

        if (straightOrSet == 0 && pair == 0 && countList.all { it == 0 }) return true

        return checkWinning(updateCountListByChangeList(countList, listOf(1, 1, 1)), straightOrSet - 1, pair) ||
                checkWinning(updateCountListByChangeList(countList, listOf(3)), straightOrSet - 1, pair) ||
                checkWinning(updateCountListByChangeList(countList, listOf(2)), straightOrSet, pair - 1)
    }

    companion object {
        var chong = 0

        @JvmStatic
        fun main(args: Array<String>) {

            val set = createWinSet()
            val ios = FileInputStream(File("allWinCaseNoRedundant.txt"))
            ios.use {
                it.bufferedReader().forEachLine {
                    val list = it.trim().removePrefix("[").removeSuffix("]").split(",").map {
                        it.trim().toInt()
                    }
                    val wrappers = list.chunked(10).map { ListWrapper(it) }
                    if (wrappers.any { !set.contains(it) }){
                        println("it is wrong: $list")
                    }

//                    if (!checkWinning(list, 4, 1)) {
//                        println(list)
//                    }
                }
//                println("chong = $chong vs size = ${set.size}")
            }
        }

        private fun checkWinning(intList: List<Int>, straightOrSet: Int, pair: Int): Boolean {
            val countList = intList.dropWhile { it == 0 }

            if (straightOrSet < 0) return false
            if (pair < 0) return false
            if (countList.any { it < 0 }) return false

            if (straightOrSet == 0 && pair == 0 && countList.all { it == 0 }) return true

            return checkWinning(updateCountListByChangeList(countList, listOf(1, 1, 1)), straightOrSet - 1, pair) ||
                    checkWinning(updateCountListByChangeList(countList, listOf(3)), straightOrSet - 1, pair) ||
                    checkWinning(updateCountListByChangeList(countList, listOf(2)), straightOrSet, pair - 1)
        }
    }
}