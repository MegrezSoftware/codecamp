/**
 * Created by muchuanxin on 2022-07-09
 */
fun main() {
    val case1 = listOf(Mahjong.WAN1, Mahjong.WAN2, Mahjong.WAN3)
    val case2 = listOf(
        Mahjong.WAN2, Mahjong.WAN2, Mahjong.WAN3, Mahjong.WAN4, Mahjong.TONG1, Mahjong.TONG2, Mahjong.TONG3, Mahjong.TONG2, Mahjong.TONG3,
        Mahjong.TONG4, Mahjong.TIAO2, Mahjong.TIAO2, Mahjong.TIAO2,
    )
    val case3 = listOf(
        Mahjong.WAN2, Mahjong.WAN2, Mahjong.WAN2, Mahjong.WAN4, Mahjong.TONG1, Mahjong.TONG2, Mahjong.TONG3, Mahjong.TONG2, Mahjong.TONG3,
        Mahjong.TONG4, Mahjong.TIAO2, Mahjong.TIAO2, Mahjong.TIAO2,
    )
    val case4 = listOf(
        Mahjong.WAN2, Mahjong.WAN2, Mahjong.WAN2, Mahjong.WAN3, Mahjong.WAN3, Mahjong.WAN4, Mahjong.TONG1, Mahjong.TONG2, Mahjong.TONG3,
        Mahjong.TONG2, Mahjong.TONG3, Mahjong.TONG4, Mahjong.TIAO2,
    )
    println("case1: ${whichCardToTing(case1).joinToString { it.name }}")
    println("case2: ${whichCardToTing(case2).joinToString { it.name }}")
    println("case3: ${whichCardToTing(case3).joinToString { it.name }}")
    println("case4: ${whichCardToTing(case4).joinToString { it.name }}")
}

/**
 * @return 返回听的牌，empty表示没有听牌
 */
fun whichCardToTing(handCards: List<Mahjong>): List<Mahjong> {
    if (handCards.size != 13)
        return emptyList()
    val counts = IntArray(31)
    handCards.forEach {
        counts[it.value]++
    }
    val results = mutableListOf<Mahjong>()
    outer@
    for (tingCard in Mahjong.values()) {
        if (worthNotToTing(counts, tingCard.value))
            continue
        val tingCounts = counts.copyOf().apply { this[tingCard.value]++ }
        for (index in tingCounts.indices) {
            // 扣除单个对子
            if (tingCounts[index] >= 2 && judgeRemainAllKeAndShun(tingCounts.copyOf().apply { this[index] -= 2 })) {
                results.add(tingCard)
                continue@outer
            }
        }
    }
    return results
}

/**
 * 判断是否值得听的牌
 * @return true不值得听
 */
fun worthNotToTing(counts: IntArray, tingCard: Int): Boolean {
    return counts[tingCard] + counts[tingCard - 1] + counts[tingCard + 1] == 0
}

/**
 * 判断剩余牌是否全是刻和顺
 */
fun judgeRemainAllKeAndShun(counts: IntArray): Boolean {
    for (index in 0..counts.lastIndex - 2) {
        if (counts[index] == 0) continue
        if (counts[index] in 1..2) {
            if (counts[index + 1] < counts[index] || counts[index + 2] < counts[index])
                return false
            else {
                counts[index + 1] -= counts[index]
                counts[index + 2] -= counts[index]
                counts[index] = 0
            }
        } else if (counts[index] == 4) {
            if (counts[index + 1] < 1 || counts[index + 2] < 1)
                return false
            else {
                counts[index + 1] -= 1
                counts[index + 2] -= 1
                counts[index] = 0
            }
        }
    }
    return counts[29] % 3 == 0
}

enum class Mahjong(val value: Int) {
    // 万1~9
    WAN1(1), WAN2(2), WAN3(3), WAN4(4), WAN5(5), WAN6(6), WAN7(7), WAN8(8), WAN9(9),

    // 筒1~9
    TONG1(11), TONG2(12), TONG3(13), TONG4(14), TONG5(15), TONG6(16), TONG7(17), TONG8(18), TONG9(19),

    // 条1~9
    TIAO1(21), TIAO2(22), TIAO3(23), TIAO4(24), TIAO5(25), TIAO6(26), TIAO7(27), TIAO8(28), TIAO9(29);

}
