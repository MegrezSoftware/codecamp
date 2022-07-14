package com.yuanfudao.megrez.app.codecamp.first

import java.util.*

data class Candidate(
    val inputSubSet: List<Mahjong>,
    val match: List<Match>,
    val remainCard: Int,
    val countMap: TreeMap<Int, Int>,
) {
}