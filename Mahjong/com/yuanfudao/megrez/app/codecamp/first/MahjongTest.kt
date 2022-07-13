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
        MahjongCreator.getWinResult(list)
    }

    @Test
    fun launchMultiPresetCaseTest() {
//        MahjongCreator.fromIntList(
//            circle = listOf(1, 1, 2, 2, 2, 4, 5),
//            line = listOf(4),
//            character = listOf(4, 5, 5, 6, 8)
//        )
//        MahjongCreator.fromString(
//            circle = "1112345678999",
//            line = "",
//            character = "",
//        )
        MahjongCreator.fromString(
            circle = "1231231234568",
            line = "",
            character = "",
        )
    }


    @Test
    fun testIndexPremutation() {
        val list = listOf(5, 3, 2)
        val res = TakeIndexCombination().permute(list)
    }
}