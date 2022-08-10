package com.tangtang.practice1

/**
 *
洗牌 shuffle tiles (or cards)

出牌 play a tile

摸牌 draw a tile

胡了 I’ve won!

吃 take a tile to complete a straight

碰 take a tile to complete a set of three

杠 take a tile to complete a set of four
 */
class Mahjong(val point: MahjongPoint, val type: MahjongType) {
    override fun toString() = "${point}${type}"

    override fun hashCode(): Int {
        return point.value + type.color.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return (other as? Mahjong)?.let {
            it.point == point && it.type == type
        } ?: false
    }

    companion object {
        val allNormalMahjongCases = enumValues<MahjongType>().flatMap { t ->
            enumValues<MahjongPoint>().map { p ->
                Mahjong(p, t)
            }
        }
    }
}

enum class MahjongPoint(val value: Int) {
    //    Empty

    One(1),
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8),
    Nine(9),
    ;

    override fun toString(): String {
        return if (value in 1..9) value.toString() else ""
    }

    companion object {
        fun parsePoint(point: Int): MahjongPoint {
            require(point in 1..9)
            return values().find { it.value == point }!!
        }
    }
}

enum class MahjongType(val color: String) {
    Character("萬"),
    Circle("筒"),
    Bamboo("条"),
    ;

    override fun toString(): String = color
}