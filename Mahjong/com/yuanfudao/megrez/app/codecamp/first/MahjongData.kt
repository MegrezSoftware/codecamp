package com.yuanfudao.megrez.app.codecamp.first

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

interface Mahjong {
    val num: Int
    val shortName: String
}

enum class Circle(override val num: Int, override val shortName: String = "筒") : Mahjong {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    ;

    override fun toString(): String {
        return "y$num"
    }
}

enum class Line(override val num: Int, override val shortName: String = "条") : Mahjong {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    ;

    override fun toString(): String {
        return "l$num"
    }
}

enum class Character(override val num: Int, override val shortName: String = "万") : Mahjong {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    ;

    override fun toString(): String {
        return "w$num"
    }
}

data class Card(val num: Mahjong, val pos: Int)

data class CompetitorStatus(
    val missing: ArrayList<Match>,
    val pair: ArrayList<Match>,
    val doubles: Int,
    val cardCount: Int,
    val matchs: List<Match>,
)

sealed class Match(val length: Int, val start: Int, val level: Int) {
    fun isMissing(): Boolean {
        return when (this) {
            is TwoSibling -> true
            is SingleOne -> true
            is Next2Next -> true
            else -> false
        }
    }

    data class SingleOne(val card: Card) : Match(1, card.pos, level = 11) {
        override fun toString(): String {
            return "[${card.num}]"
        }
    }

    data class Doubles(val card: Card) : Match(2, card.pos, level = 19) {
        override fun toString(): String {
            return "[${card.num}${card.num}]"
        }
    }

    data class Triples(val card: Card) : Match(3, card.pos, level = 20) {
        override fun toString(): String {
            return "[${card.num}${card.num}${card.num}]"
        }
    }

    data class TwoSibling(val first: Card, val second: Card) :
        Match(length = 2, first.pos, level = 18) {
        override fun toString(): String {
            return "[${first.num}${second.num}]"
        }
    }

    data class ThreeCompany(val first: Card, val second: Card, val third: Card) :
        Match(length = 3, first.pos, level = 20) {
        override fun toString(): String {
            return "[${first.num}${second.num}${third.num}]"
        }
    }

    data class Next2Next(val first: Card, val third: Card, val missing: Int) :
        Match(3, first.pos, level = 18) {
        override fun toString(): String {
            return "[${first.num}(*${missing})${third.num}]"
        }
    }

    data class None(val pos: Int) : Match(length = 0, pos, level = 10) {
        override fun toString(): String {
            return "[无(pos=$pos)]"
        }
    }
}
