/**
 * Created by muchuanxin on 2022-07-09
 */
fun main() {
    val omnipotentCards =
        listOf(Mahjong.SPRING, Mahjong.SUMMER, Mahjong.AUTUMN, Mahjong.WINTER, Mahjong.PLUM, Mahjong.ORCHID, Mahjong.BAMBOO, Mahjong.CHRYSANTHEMUM)
    require(judgeTingCards("223344-234-2234", "--25", omnipotentCards))
    require(judgeTingCards("1112345678999", "123456789", omnipotentCards))
    require(judgeTingCards("23456-22233-789", "147", omnipotentCards))
    require(judgeTingCards("123456-23444-55", "-14-5", omnipotentCards))
    require(judgeTingCards("1112378999-123--", "1469", omnipotentCards))
    require(judgeTingCards("2344445688999", "178", omnipotentCards))
    require(judgeTingCards("223344-334--222-1", "-23456", omnipotentCards))
    require(judgeTingCards("1234489-147--1234", "null", omnipotentCards))
    require(judgeTingCards("-123456789---1234", "all", omnipotentCards))
}

fun judgeTingCards(input: String, output: String, omnipotentCards: List<Mahjong>): Boolean {
    val realInput = convertStringToMahjongList(input, omnipotentCards)
    return whichCardToTing(realInput, omnipotentCards) == convertStringToMahjongList(output, omnipotentCards)
}

private fun convertStringToMahjongList(input: String, omnipotentCards: List<Mahjong>): List<Mahjong> {
    if (input == "null") return emptyList()
    if (input == "all") return Mahjong.values().filter { it !in omnipotentCards }
    val result = mutableListOf<Mahjong>()
    input.split("-").forEachIndexed { index, s ->
        s.forEach { c ->
            Mahjong.parse(index * 10 + c.digitToInt())?.also {
                result.add(it)
            } ?: throw IllegalArgumentException()
        }
    }
    return result
}

/**
 * @return 返回听的牌，empty表示没有听牌
 */
fun whichCardToTing(handCards: List<Mahjong>, omnipotentCards: List<Mahjong>): List<Mahjong> {
    if (handCards.size != 13)
        return emptyList()
    // 过滤万能牌
    val remainCards = handCards.filter { it !in omnipotentCards }
    // 统计万能牌个数
    val omnipotentCount = handCards.count { it in omnipotentCards }
    val counts = IntArray(40)
    remainCards.forEach {
        counts[it.value]++
    }
    val results = mutableListOf<Mahjong>()
    for (tingCard in Mahjong.values().filter { it !in omnipotentCards }) {
        if (worthNotToTing(counts, tingCard.value, omnipotentCount))
            continue
        val tingCounts = counts.copyOf().apply { this[tingCard.value]++ }
        if (judgeHu(tingCounts, 4, 1, omnipotentCount)) {
            results.add(tingCard)
        }
    }
    return results
}

/**
 * 判断是否值得听的牌
 * @return true不值得听
 */
fun worthNotToTing(counts: IntArray, tingCard: Int, omnipotentCount: Int): Boolean {
    return counts[tingCard] + counts[tingCard - 1] + counts[tingCard + 1] + omnipotentCount == 0 || counts[tingCard] == 4
}

fun judgeHu(counts: IntArray, remainShunKeCount: Int, remainDuiCount: Int, omnipotentCount: Int): Boolean {
    if ((remainShunKeCount == 0 && remainDuiCount == 0 && counts.sum() == 0 && omnipotentCount == 0) || (counts.sum() == 0 && omnipotentCount == 3 * remainShunKeCount + 2 * remainDuiCount)) return true
    var ke = false
    var shun = false
    var dui = false
    if (remainShunKeCount > 0) {
        val keIndex = counts.indexOfFirst { it > 0 && it + omnipotentCount >= 3 }
        if (keIndex != -1) {
            val consume = (3 - counts[keIndex]).coerceAtLeast(0)
            val newCounts = counts.copyOf().apply { this[keIndex] -= (3 - consume) }
            ke = judgeHu(newCounts, remainShunKeCount - 1, remainDuiCount, omnipotentCount - consume)
        }
        for (index in (IntRange(1, 7) + IntRange(11, 17) + IntRange(21, 27))) {
            val consume = (1 - counts[index]).coerceAtLeast(0) + (1 - counts[index + 1]).coerceAtLeast(0) + (1 - counts[index + 2]).coerceAtLeast(0)
            if (counts[index] > 0 && omnipotentCount - consume >= 0) {
                val newCounts = counts.copyOf().apply {
                    this[index] = (this[index] - 1).coerceAtLeast(0)
                    this[index + 1] = (this[index + 1] - 1).coerceAtLeast(0)
                    this[index + 2] = (this[index + 2] - 1).coerceAtLeast(0)
                }
                shun = judgeHu(newCounts, remainShunKeCount - 1, remainDuiCount, omnipotentCount - consume)
                break
            }
        }
    }
    if (remainDuiCount > 0) {
        val duiIndex = counts.indexOfFirst { it > 0 && it + omnipotentCount >= 2 }
        if (duiIndex != -1) {
            val consume = (2 - counts[duiIndex]).coerceAtLeast(0)
            val newCounts = counts.copyOf().apply { this[duiIndex] -= (2 - consume) }
            dui = judgeHu(newCounts, remainShunKeCount, remainDuiCount - 1, omnipotentCount - consume)
        }
    }
    return ke || shun || dui
}

enum class Mahjong(val value: Int) {
    // 万1~9
    WAN1(1), WAN2(2), WAN3(3), WAN4(4), WAN5(5), WAN6(6), WAN7(7), WAN8(8), WAN9(9),

    // 筒1~9
    TONG1(11), TONG2(12), TONG3(13), TONG4(14), TONG5(15), TONG6(16), TONG7(17), TONG8(18), TONG9(19),

    // 条1~9
    TIAO1(21), TIAO2(22), TIAO3(23), TIAO4(24), TIAO5(25), TIAO6(26), TIAO7(27), TIAO8(28), TIAO9(29),

    // 字牌，东南西北中发白
    EAST(31), SOUTH(32), WEST(33), NORTH(34), ZHONG(35), FA(36), BAI(37),

    // 花牌，春夏秋冬梅兰竹菊
    SPRING(41), SUMMER(42), AUTUMN(43), WINTER(44), PLUM(45), ORCHID(46), BAMBOO(47), CHRYSANTHEMUM(48);

    companion object {
        fun parse(value: Int): Mahjong? {
            return values().find { it.value == value }
        }
    }
}
