package com.tangtang.practice2

import com.tangtang.data.MahjongCard
import com.tangtang.practice1.ListWrapper
import com.tangtang.practice1.createWinSet

fun main(args: Array<String>) {
    val winSet = createWinSet()
    val listWrappers = map2ListWrappers()

    val powerCardCount = 14 - (listWrappers.first.list.sum() + listWrappers.second.list.sum() + listWrappers.third.list.sum())
    println("powerCardCount=$powerCardCount")
    val first = map2NeedCountSet(winSet, listWrappers.first, powerCardCount)
    val second = map2NeedCountSet(winSet, listWrappers.second, powerCardCount)
    val third = map2NeedCountSet(winSet, listWrappers.third, powerCardCount)

    first.forEach { f ->
        second.forEach { s ->
            third.forEach { t ->
                if (f + s + t == powerCardCount) {
                    println("it is ready to win.")
                    return
                }
            }
        }
    }
    println("it is not ready to win.")
}

fun map2NeedCountSet(winSet: Set<ListWrapper>, it: ListWrapper, powerCardCount: Int): Set<Int> {
    return winSet.mapNotNull { s ->
        val result = s.list.zip(it.list) { a, b -> a - b }
        val sum = result.sum()
        if (result.any { c -> c < 0 } || sum > powerCardCount) {
            null
        } else {
            sum
        }
    }.toSet()
}

fun createListWrapper(list: List<MahjongCard>): ListWrapper {
    val intList = MutableList(10) { 0 }
    list.forEach {
        intList[it.order % 10]++
    }
    return ListWrapper(intList)
}

fun map2ListWrappers(): Triple<ListWrapper, ListWrapper, ListWrapper> {
    return Triple(
            createListWrapper(listOf(
                    MahjongCard.Character1,
                    MahjongCard.Character9,
            )),
            createListWrapper(listOf(
                    MahjongCard.Circle5)
            ),
            createListWrapper(listOf(
                    MahjongCard.Bamboo1,
                    MahjongCard.Bamboo9,
            )),
    )
}