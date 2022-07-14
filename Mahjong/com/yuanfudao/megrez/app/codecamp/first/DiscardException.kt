package com.yuanfudao.megrez.app.codecamp.first

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