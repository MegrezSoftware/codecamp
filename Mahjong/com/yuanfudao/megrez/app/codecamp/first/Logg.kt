package com.yuanfudao.megrez.app.codecamp.first

object Logg {
    private const val isDebug = false
    private const val printCombine = false
    fun debugLn(s: String) {
        if (isDebug) println(s)
    }
    fun combineLn(s: String) {
        if (printCombine) println(s)
    }
}
