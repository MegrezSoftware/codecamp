package com.yuanfudao.megrez.app.codecamp.second

interface Mahjong {
    val num: Int
    val shortName: String
    val internalOffset: Int  // 用于更好定位

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