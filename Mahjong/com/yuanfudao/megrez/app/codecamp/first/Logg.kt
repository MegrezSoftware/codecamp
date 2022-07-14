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

fun <T> Result<T>.printError() {
    this.onFailure {
        if (it is DiscardException) {
            Logg.debugLn("${it.msg}")
        } else {
            it.printStackTrace()
        }
    }
}

data class DiscardException(val msg: String? = "", val throws: Throwable? = null) :
    java.lang.IllegalStateException(msg, throws)