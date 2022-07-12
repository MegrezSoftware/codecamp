package com.yuanfudao.megrez.app.codecamp.first

object Logg {
    private const val isDebug = true
    fun debugLn(s: String) {
        if (isDebug) println(s)
    }
}
