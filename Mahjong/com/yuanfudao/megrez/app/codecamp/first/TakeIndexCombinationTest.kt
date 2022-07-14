package com.yuanfudao.megrez.app.codecamp.first

import org.junit.Test

class TakeIndexCombinationTest() {
    @Test
    fun testIndexPremutation() {
        val list = listOf(5, 3, 2)
        val res = TakeIndexCombination().permute(list)
    }
}