package com.yuanfudao.megrez.app.codecamp.third

interface Mahjong {
    val num: Int
    val shortName: String
    val internalOffset: Int  // 用于更好定位

    fun toInnerIndex() = num + internalOffset

}

fun Int.innerIndexToMahjong(): Mahjong? {
    val innerIndex = this
    val num = innerIndex % 10
    return if (innerIndex > Chars.ONE.internalOffset) {
        Chars.values().find { it.num == num }
    } else if (innerIndex > Line.ONE.internalOffset) {
        Line.values().find { it.num == num }
    } else if (innerIndex > Circle.ONE.internalOffset) {
        Circle.values().find { it.num == num }
    } else if (innerIndex > Wan.ONE.internalOffset) {
        Wan.values().find { it.num == num }
    } else null
}

enum class Chars(
    override val num: Int,
    override val shortName: String = "字牌",
    override val internalOffset: Int = 30,
) : Mahjong {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    ;

    override fun toString(): String {
        return "z$num"
    }
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

enum class Wan(
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