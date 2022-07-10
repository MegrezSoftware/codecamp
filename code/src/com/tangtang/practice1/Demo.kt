package com.tangtang.practice1

import java.io.File
import java.lang.Exception

fun main(args: Array<String>) {
    val dataList = extractData("code/src/com/tangtang/practice1/testCases.txt")
    dataList.map { HandTile(it.first) to it.second }.forEach(::info)
}

private fun info(pair: Pair<HandTile, String>) {
    val (handTile, txt) = pair
    val list = handTile.checkReady()
    if (list.isEmpty()) {
        println("it is not ready for winning.(HandTile: $txt)")
    } else {
        println("it is ready : $list (HandTile: $txt)")
    }
}

fun extractData(path: String): List<Pair<List<Mahjong>, String>> {
    val file = File(path)
    println("path= ${file.absolutePath}")
    return file.readLines().filter { it.isNotBlank() }.map {
        createMahjong(it) to it
    }
}

fun createMahjong(txt: String): List<Mahjong> {
    return txt.split(";").mapNotNull {
        try {
            val s = it.split("-")
            Mahjong.create(s[0].toInt(), s[1])
        } catch (e: Exception) {
            null
        }
    }
}