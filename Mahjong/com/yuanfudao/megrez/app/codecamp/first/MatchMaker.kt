package com.yuanfudao.megrez.app.codecamp.first

/**
 * Created by lei.jialin on 2022/7/13
 */
object MatchMaker {
    /**
     * @param start which index the window start from. Window will
     * take three element into account at most each time
     * to consider if it's a valid match.
     *
     * @param sorted must be a sorted list. If the sorted list's size is less than 3, normally the
     * param 'start' will be 0.
     * */
    fun makeMatch(list: List<Mahjong>): Match {
        return fromSubList(list, 0)
    }

    private fun fromSubList(sorted: List<Mahjong>, start: Int): Match {
        val a1 = sorted.getOrNull(start)
        val a2 = sorted.getOrNull(start + 1)
        val a3 = sorted.getOrNull(start + 2)
        val a4 = sorted.getOrNull(start + 3)
        a1 ?: return Match.None(start)
        return when {
            a2 == null -> {
                Match.SingleOne(card = a1)
            }
            a1 == a2 && a1 == a3 && a3 == a4 -> {
                Match.Fours(card = a1)
            }
            a1 == a2 && a1 == a3 -> {
                Match.Triples(card = a1)
            }
            a1 == a2 -> {
                Match.Doubles(card = a1)
            }
            (a3?.num == a2.num + 1) && (a2.num == a1.num + 1) -> {
                Match.ThreeCompany(
                    first = a1,
                    second = a2,
                    third = a3
                )
            }
            (a2.num == a1.num + 1) -> {
                Match.TwoSibling(
                    first = a1,
                    second = a2,
                )
            }
            (a2.num == a1.num + 2) -> {
                Match.Next2Next(
                    first = a1,
                    third = a2,
                    missing = a1.num + 1,
                )
            }
            a3?.num == a2.num + 1 -> {
                Match.TwoSibling(
                    first = a2,
                    second = a3,
                )
            }
            else -> Match.None(start)
        }

    }

}