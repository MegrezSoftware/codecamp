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
sealed class Mahjong(val value: Int, val type: String) {
    override fun toString() = "$value$type"

    override fun hashCode(): Int {
        return value + type.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return (other as? Mahjong)?.let {
            it.value == value && it.type == type
        } ?: false
    }

    fun previous(): Mahjong? {
        if (value == 1) return null

        val v = value - 1
        return when (this) {
            is Character -> Character(v)
            is Circle -> Circle(v)
            is Bamboo -> Bamboo(v)
        }
    }

    fun next(): Mahjong? {
        if (value == 9) return null

        val v = value + 1
        return when (this) {
            is Character -> Character(v)
            is Circle -> Circle(v)
            is Bamboo -> Bamboo(v)
        }
    }

    companion object {
        fun create(value: Int, type: String): Mahjong? {
            if (value < 1 || value > 9) return null
            return when (type) {
                "萬" -> Character(value)
                "筒" -> Circle(value)
                "条" -> Bamboo(value)
                else -> null
            }
        }
    }
}

class Character(value: Int) : Mahjong(value, "萬")
class Circle(value: Int) : Mahjong(value, "筒")
class Bamboo(value: Int) : Mahjong(value, "条")

