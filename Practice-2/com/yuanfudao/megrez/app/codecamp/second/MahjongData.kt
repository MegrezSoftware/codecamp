package com.yuanfudao.megrez.app.codecamp.second

interface Mahjong {
    val num: Int
    val shortName: String
    val internalOffset: Int

    fun toInnerIndex() = num + internalOffset
}


enum class Circle(
    override val num: Int,
    override val shortName: String = "筒",
    override val internalOffset: Int = 10,
) : Mahjong {
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

enum class Line(
    override val num: Int,
    override val shortName: String = "条",
    override val internalOffset: Int = 20,
) : Mahjong {
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

enum class Character(
    override val num: Int,
    override val shortName: String = "万",
    override val internalOffset: Int = 0,
) : Mahjong {
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

    data class SingleOne(val card: Card) :
        Match(length = 1, level = 11, mahjongList = listOf(card.mahjong)) {
        override fun toString(): String {
            return "[${card.mahjong}]"
        }
    }

    data class Doubles(val card: Card) :
        Match(length = 2, level = 19, mahjongList = (0 until 2).map { card.mahjong }) {
        override fun toString(): String {
            return "[${card.mahjong}${card.mahjong}]"
        }
    }

    data class Triples(val card: Card) :
        Match(length = 3, level = 20, mahjongList = (0 until 3).map { card.mahjong }) {
        override fun toString(): String {
            return "[${card.mahjong}${card.mahjong}${card.mahjong}]"
        }
    }

    data class Fours(val card: Card) :
        Match(length = 4, level = 20, mahjongList = (0 until 4).map { card.mahjong }) {
        override fun toString(): String {
            return "[${card.mahjong}${card.mahjong}${card.mahjong}]"
        }
    }

    data class TwoSibling(val first: Card, val second: Card) :
        Match(
            length = 2,
            level = 18,
            mahjongList = listOf(first.mahjong, second.mahjong),
        ) {
        override fun toString(): String {
            return "[${first.mahjong}${second.mahjong}]"
        }
    }

    data class ThreeCompany(val first: Card, val second: Card, val third: Card) :
        Match(
            length = 3,
            level = 20,
            mahjongList = listOf(first.mahjong, second.mahjong),
        ) {
        override fun toString(): String {
            return "[${first.mahjong}${second.mahjong}${third.mahjong}]"
        }
    }

    data class Next2Next(val first: Card, val third: Card, val missing: Int) :
        Match(3, level = 18, mahjongList = listOf(first.mahjong, third.mahjong)) {
        override fun toString(): String {
            return "[${first.mahjong}(*${missing})${third.mahjong}]"
        }
    }

    data class None(val pos: Int) : Match(length = 0, level = 10, mahjongList = emptyList()) {
        override fun toString(): String {
            return "[无(pos=$pos)]"
        }
    }
}
