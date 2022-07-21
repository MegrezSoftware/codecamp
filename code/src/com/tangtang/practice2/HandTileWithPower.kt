package com.tangtang.practice2

import com.tangtang.practice1.*


class HandTileWithPower(
    data: List<Mahjong>,
    private val powerTileCount: Int = 13 - data.size
) : HandTile(data) {

    override fun checkReady(): Set<Mahjong> {
        val readies = mutableSetOf<Mahjong>()
        mahjong13Array.forEachIndexed { index, count ->
            val value = index % 10
            if (value != 0 && count < 4) {
                mahjong13Array[index]++
                if (checkWinning(mahjong13Array, 4, 1)) {
                    val winMahjong = when {
                        index < 10 -> Character(value)
                        index > 20 -> Bamboo(value)
                        else -> Circle(value)
                    }
                    readies.add(winMahjong)
                }
                mahjong13Array[index]--
            }
        }
        return readies
    }

    private fun checkWinning(mahjong13Array: IntArray, straightOrSet: Int, pair: Int): Boolean {
        if (straightOrSet < 0) return false
        if (pair < 0) return false

        val neededPowerTileCount = mahjong13Array.sumOf { if (it > 0) 0 else -it }
        val restPowerTileCount = powerTileCount - neededPowerTileCount
        if (restPowerTileCount < 0) return false

        if (restPowerTileCount == 0 && straightOrSet == 0 && pair == 0) {
            return true
        }

        mahjong13Array.forEachIndexed { index, count ->
            val tileIdx = index % 10
            var isWinning = false
            // set
            if (tileIdx != 0) {
                mahjong13Array[index] = count - 3
                isWinning = checkWinning(mahjong13Array, straightOrSet - 1, pair)
                mahjong13Array[index] = count
                if (isWinning) return true
            }

            // straight
            if (tileIdx in 2..8) {
                mahjong13Array[index - 1]--
                mahjong13Array[index]--
                mahjong13Array[index + 1]--
                isWinning = checkWinning(mahjong13Array, straightOrSet - 1, pair)
                mahjong13Array[index - 1]++
                mahjong13Array[index]++
                mahjong13Array[index + 1]++
                if (isWinning) return true
            }

            // pair
            if (tileIdx != 0) {
                mahjong13Array[index] = count - 2
                isWinning = checkWinning(mahjong13Array, straightOrSet, pair - 1)
                mahjong13Array[index] = count
                if (isWinning) return true
            }
        }
        return false
    }
}