package com.yuanfudao.megrez.app.codecamp.first

/**
 * Created by lei.jialin on 2022/7/12
 */
object MahjongCreator {
    /***
     * @throws IllegalArgumentException when the input size sumups are not equals to 13.
     * */
    @Throws(IllegalArgumentException::class.java)
    fun fromString(circle: String, line: String, character: String) {
        fromIntList(
            circle = circle.split("").filter { it.isNotBlank() }.map { it.toInt() },
            line = line.split("").filter { it.isNotBlank() }.map { it.toInt() },
            character = character.split("").filter { it.isNotBlank() }.map { it.toInt() },
        )
    }

    /***
     * @throws IllegalArgumentException when the input size sumups are not equals to 13.
     * */
    @Throws(IllegalArgumentException::class.java)
    fun fromIntList(circle: List<Int>, line: List<Int>, character: List<Int>) {
        val list = ArrayList<Mahjong>()
        circle.forEach { num ->
            Circle.values().find { it.num == num }?.run { list.add(this) }
        }
        line.forEach { num ->
            Line.values().find { it.num == num }?.run { list.add(this) }
        }
        character.forEach { num ->
            Character.values().find { it.num == num }?.run { list.add(this) }
        }
        require(list.size == 13) {
            "size=${list.size}不是13，list=$list"
        }
        MahjongGame().checkWin(list)
    }
}