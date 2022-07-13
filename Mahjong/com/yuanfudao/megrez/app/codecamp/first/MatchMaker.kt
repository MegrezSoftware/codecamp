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
                Match.SingleOne(Card(mahjong = a1))
            }
            a1 == a2 && a1 == a3 && a3 == a4-> {
                Match.Fours(Card(mahjong = a1))
            }
            a1 == a2 && a1 == a3 -> {
                Match.Triples(Card(mahjong = a1))
            }
            a1 == a2 -> {
                Match.Doubles(Card(mahjong = a1))
            }
            (a3?.num == a2.num + 1) && (a2.num == a1.num + 1) -> {
                Match.ThreeCompany(
                    first = Card(mahjong = a1),
                    second = Card(mahjong = a2),
                    third = Card(mahjong = a3)
                )
            }
            (a2.num == a1.num + 1) -> {
                Match.TwoSibling(
                    first = Card(mahjong = a1),
                    second = Card(mahjong = a2),
                )
            }
            (a2.num == a1.num + 2) -> {
                Match.Next2Next(
                    first = Card(mahjong = a1),
                    third = Card(mahjong = a2),
                    missing = a1.num + 1,
                )
            }
            a3?.num == a2.num + 1 -> {
                Match.TwoSibling(
                    first = Card(mahjong = a2),
                    second = Card(mahjong = a3),
                )
            }
            else -> Match.None(start)
        }

    }

}