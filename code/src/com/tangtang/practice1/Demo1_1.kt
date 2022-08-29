package com.tangtang.practice1

import com.tangtang.data.MahjongCard
import java.io.File
import java.io.FileOutputStream

fun main(args: Array<String>) {
    val fos = FileOutputStream(File("allWinCase4.txt"))
    val start = System.currentTimeMillis()
    val set = createWinSet()
    val end = System.currentTimeMillis()
    println("end = $end")
    println("total = ${end - start}")

    fos.use {
        set.forEach {
            fos.write("${it.list}\n".toByteArray())
        }
    }
    println(set.size)
    println(sum)
}

var sum = 0

val allCardIntList = MutableList(30) {
    val card = MahjongCard.values().find { c -> it == c.order }
    if (card == null) {
        0
    } else {
        4
    }
}

fun createWinSet(): Set<ListWrapper> {
    val set = mutableSetOf<ListWrapper>()
    val list = MutableList(10) { if (it == 0) 0 else 4 }
    (0..4).map {
        isWin(0, list, it, 0, set)
        isWin(0, list, it, 1, set)
    }
    return set
}

fun isWin(offset: Int, arr: List<Int>, set: Int, pair: Int, wrappers: MutableSet<ListWrapper>) {
    if (offset > arr.size) return
    if (set < 0) return
    if (pair < 0) return

    if (set == 0 && pair == 0) {
        record(arr, wrappers)
        return
    }

    for (i in offset until arr.size) {

        if (arr[i] >= 3) {
            val arrSet = arr.toMutableList()
            arrSet[i] -= 3
            val newOffset = if (arrSet[i] > 0) i else i + 1
            isWin(newOffset, arrSet, set - 1, pair, wrappers)
        }

        if (arr[i] >= 2) {
            val arrSet = arr.toMutableList()
            arrSet[i] -= 2
            val newOffset = if (arrSet[i] > 0) i else i + 1
            isWin(newOffset, arrSet, set, pair - 1, wrappers)
        }

        if (i + 2 < arr.size && arr[i] > 0 && arr[i + 1] > 0 && arr[i + 2] > 0) {
            val arrSet = arr.toMutableList()
            arrSet[i]--
            arrSet[i + 1]--
            arrSet[i + 2]--
            val newOffset = if (arrSet[i] > 0) i else i + 1
            isWin(newOffset, arrSet, set - 1, pair, wrappers)
        }

    }

}

fun record(arr: List<Int>, set: MutableSet<ListWrapper>) {
    sum++
    val winList = allCardIntList.zip(arr) { a, b -> a - b }
    set.add(ListWrapper(winList))
}

data class ListWrapper(val list: List<Int>) {
    override fun hashCode(): Int {
        var sum = 0
        var o = 1
        list.forEachIndexed { index, i ->
            sum += i * index * o
            o *= 10
        }
        return sum
    }

    override fun equals(other: Any?): Boolean {
        return (other as? ListWrapper)?.let {
            it.list.zip(list).all { it.first == it.second }
        } ?: false
    }
}

/**
 * try to promote isWin
 */
fun isWin2(arr: List<Int>, set: Int, pair: Int, wrappers: MutableSet<ListWrapper>) {
    if (set < 0) return
    if (pair < 0) return

    if (set == 0 && pair == 0) {
        record(arr, wrappers)
        return
    }

    for (i in arr.indices) {

        if (arr[i] >= 3) {
            val arrSet = arr.toMutableList()
            arrSet[i] -= 3
            arrSet.dropWhile { it == 0 }
            isWin2(arrSet, set - 1, pair, wrappers)
        }

        if (arr[i] >= 2) {
            val arrSet = arr.toMutableList()
            arrSet[i] -= 2
            arrSet.dropWhile { it == 0 }
            isWin2(arrSet, set, pair - 1, wrappers)
        }

        if (i + 2 < arr.size && arr[i] > 0 && arr[i + 1] > 0 && arr[i + 2] > 0) {
            val arrSet = arr.toMutableList()
            arrSet[i]--
            arrSet[i + 1]--
            arrSet[i + 2]--
            arrSet.dropWhile { it == 0 }
            isWin2(arrSet, set - 1, pair, wrappers)
        }
    }
}