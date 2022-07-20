package com.yuanfudao.megrez.app.codecamp.first

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MahjongTest {
    @Test
    fun launchOneRandomCasesTest() {
        val list = ArrayList<Mahjong>()
        val gene = java.util.Random(System.currentTimeMillis())
        for (i in 0 until 13) {
            val type = gene.nextInt(99)
            val value = gene.nextInt(80) % 8 + 1
            when (type % 3) {
                0 -> list.add(Character.values().find { it.num == value }!!)
                1 -> list.add(Line.values().find { it.num == value }!!)
                2 -> list.add(Circle.values().find { it.num == value }!!)
            }
        }
        MahjongCreator.tellMeWhichCardToWin(list)
    }

    @Test
     fun launchMultiPresetCaseTest() {
        // TODO 目前只支持一个种类的牌输入
        MahjongCreator.fromString(
            circle = "1112345678999",
            line = "",
            character = "",
        )
        MahjongCreator.fromString(
            circle = "1231231234568",
            line = "",
            character = "",
        )
    }
}
