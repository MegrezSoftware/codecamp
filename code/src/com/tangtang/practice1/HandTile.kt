package com.tangtang.practice1


class HandTile(data: List<Mahjong>) {
    private val mahjong13Array: IntArray = IntArray(30)

    init {
        data.forEach {
            val index = when (it) {
                is Character -> it.value
                is Circle -> 10 + it.value
                is Bamboo -> 20 + it.value
            }
            mahjong13Array[index]++
        }
    }

    fun checkReady(): Set<Mahjong> {
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
        if (mahjong13Array.any { it < 0 }) return false

        if (straightOrSet == 0 && pair == 0 && mahjong13Array.all { it == 0 }) return true

        mahjong13Array.forEachIndexed { index, count ->
            var isWinning = false
            // set
            mahjong13Array[index] = count - 3
            isWinning = checkWinning(mahjong13Array, straightOrSet - 1, pair)
            mahjong13Array[index] = count
            if (isWinning) return true

            // straight
            if (index % 10 in 2..8) {
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
            mahjong13Array[index] = count - 2
            isWinning = checkWinning(mahjong13Array, straightOrSet, pair - 1)
            mahjong13Array[index] = count
            if (isWinning) return true
        }
        return false
    }
}