package com.yuanfudao.megrez.app.codecamp.first

import java.util.*

data class Candidate(
    val inputSubSet: List<Mahjong>,
    val match: List<Match>,
    val remainCard: Mahjong,
    val countMap: TreeMap<Int, Int>,
)


data class CalcMissingInfo(
    val missing: HashSet<Int>,
    val doubles: LinkedList<Match.Doubles>,
    val single: LinkedList<Match.SingleOne>,
    val threes: LinkedList<Match>,
    val twoSibling: LinkedList<Match.TwoSibling>,
    val next2Next: LinkedList<Match.Next2Next>,
    val cardCount: Int,
)