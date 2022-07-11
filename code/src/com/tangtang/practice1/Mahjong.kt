package com.tangtang.practice1

import kotlin.math.pow

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
        fun create(v: Int, t: String): Mahjong? {
            if (v < 1 || v > 9) return null
            return when (t) {
                "萬" -> Character(v)
                "筒" -> Circle(v)
                "条" -> Bamboo(v)
                else -> null
            }
        }
    }
}

class Character(value: Int) : Mahjong(value, "萬")
class Circle(value: Int) : Mahjong(value, "筒")
class Bamboo(value: Int) : Mahjong(value, "条")

