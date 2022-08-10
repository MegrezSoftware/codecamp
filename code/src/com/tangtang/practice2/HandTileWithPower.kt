package com.tangtang.practice2

import com.tangtang.practice1.*


class HandTileWithPower(private val data: List<Mahjong>, private val powerTileCount: Int = 13 - data.size) {

    init {
        println("$powerTileCount ${data.size}")
    }

    fun checkReady(): List<Mahjong> = Mahjong.allNormalMahjongCases.filter {
        isWinning(data + it, powerTileCount)
    }

    private fun isWinning(mahjongList: List<Mahjong>, powerTileCount: Int): Boolean = mahjongList.map2SortedList().let {
        if (it.sum() > 14 || it.any { c -> c > 4 }) {
            false
        } else {
            val dividerList = mahjongList.createDividerSet(0)
            checkWinning(it, 4, 1, powerTileCount, dividerList)
        }
    }

    private fun checkWinning(intList: List<Int>, straightOrSet: Int, pair: Int, totalPowerTile: Int, dividerList: List<Int>): Boolean {
        if (!isDividerValid(dividerList, intList)) return false

        val needPowerTile = intList.sumOf { if (it < 0) -it else 0 }
        val restPowerTile = totalPowerTile - needPowerTile

        if (restPowerTile < 0) return false
        if (straightOrSet < 0) return false
        if (pair < 0) return false

        if (straightOrSet == 0 && pair == 0 && restPowerTile == 0) return true

        val countList = intList.dropWhile { it <= 0 }
        if (countList.isEmpty() && (restPowerTile == straightOrSet * 3 + pair * 2)) return true

        return checkWinning(updateCountListByChangeList(countList, listOf(1, 1, 1)), straightOrSet - 1, pair, restPowerTile, dividerList) ||
                checkWinning(updateCountListByChangeList(countList, listOf(3)), straightOrSet - 1, pair, restPowerTile, dividerList) ||
                checkWinning(updateCountListByChangeList(countList, listOf(2)), straightOrSet, pair - 1, restPowerTile, dividerList)
    }

    private fun isDividerValid(dividerList: List<Int>, intList: List<Int>): Boolean {
        val size = dividerList.last()
        val offset = size - intList.size
        val ids = dividerList.map { it - offset }
        return ids.all {
            intList.getOrNull(it)?.let {
                it == 0
            } ?: true
        }
    }
}