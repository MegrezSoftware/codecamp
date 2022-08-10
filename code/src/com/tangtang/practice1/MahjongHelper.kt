package com.tangtang.practice1

fun List<Mahjong>.map2SortedList(): List<Int> {
    val arraySize = MahjongType.values().size * 10
    val mahjongArray = IntArray(arraySize)
    forEach {
        val index = when (it.type) {
            MahjongType.Character -> it.point.value + 0
            MahjongType.Circle -> it.point.value + 10
            MahjongType.Bamboo -> it.point.value + 20
        }
        mahjongArray[index]++
    }
    return mahjongArray.asList()
}

fun List<Mahjong>.createDividerSet(windCount: Int): List<Int> = mutableListOf(0, 10, 20, 30).apply {
    addAll((0 until windCount).map { 30 + it * 2 })
}

fun updateCountListByChangeList(countList: List<Int>, changeList: List<Int>): List<Int> {
    return countList.take(changeList.size).zip(changeList) { s, c -> s - c } + countList.drop(changeList.size)
}

fun fillLackingInt(countList: List<Int>, totalCount: Int): Pair<List<Int>, Int> {
    var sum = totalCount
    return countList.map {
        if (it < 0) {
            sum += it
            0
        } else {
            it
        }
    } to sum
}