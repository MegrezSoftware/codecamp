package com.yuanfudao.megrez.app.codecamp.first

interface Mahjong {
    val num: Int
    val shortName: String

    companion object {
        fun createMahjong(
            data: Class<*>,
            num: Int
        ): Mahjong? {
            return when (data) {
                Circle::class.java -> Circle.values().find { it.num == num }
                Line::class.java -> Line.values().find { it.num == num }
                Character::class.java -> Character.values().find { it.num == num }
                else -> null
            }
        }
    }
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

data class Card(val mahjong: Mahjong)

data class CompetitorStatus(
    val missing: ArrayList<Match>,
    val pair: ArrayList<Match>,
    val doubles: Int,
    val cardCount: Int,
    val matchs: List<Match>,
)

sealed class Match(val length: Int, val level: Int, val mahjongList: List<Mahjong>) {
    fun isMissing(): Boolean {
        return when (this) {
            is TwoSibling -> true
            is SingleOne -> true
            is Next2Next -> true
            else -> false
        }
    }

    data class SingleOne(val card: Mahjong) :
        Match(length = 1, level = 11, mahjongList = listOf(card)) {
        override fun toString(): String {
            return "[${card}]"
        }
    }

    data class Doubles(val card: Mahjong) :
        Match(length = 2, level = 19, mahjongList = (0 until 2).map { card }) {
        override fun toString(): String {
            return "[${card}${card}]"
        }
    }

    data class Triples(val card: Mahjong) :
        Match(length = 3, level = 20, mahjongList = (0 until 3).map { card }) {
        override fun toString(): String {
            return "[${card}${card}${card}]"
        }
    }

    data class Fours(val card: Mahjong) :
        Match(length = 4, level = 20, mahjongList = (0 until 4).map { card }) {
        override fun toString(): String {
            return "[${card}${card}${card}]"
        }
    }

    data class TwoSibling(val first: Mahjong, val second: Mahjong) :
        Match(
            length = 2,
            level = 18,
            mahjongList = listOf(first, second),
        ) {
        override fun toString(): String {
            return "[${first}${second}]"
        }
    }

    data class ThreeCompany(val first: Mahjong, val second: Mahjong, val third: Mahjong) :
        Match(
            length = 3,
            level = 20,
            mahjongList = listOf(first, second),
        ) {
        override fun toString(): String {
            return "[${first}${second}${third}]"
        }
    }

    data class Next2Next(val first: Mahjong, val third: Mahjong, val missing: Int) :
        Match(3, level = 18, mahjongList = listOf(first, third)) {
        override fun toString(): String {
            return "[${first}(*${missing})${third}]"
        }
    }

    data class None(val pos: Int) : Match(length = 0, level = 10, mahjongList = emptyList()) {
        override fun toString(): String {
            return "[无(pos=$pos)]"
        }
    }
}
