package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 */
/**
 * 麻将类型
 */
enum class MahjongType(val value: Int) {
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
        fun parse(value: Int): MahjongType? {
            return values().find { it.value == value }
        }

        fun getBaseMahjong(): List<MahjongType> {
            return (1..29).mapNotNull { parse(it) }
        }

        fun getZiMahjong(): List<MahjongType> {
            return (31..37).mapNotNull { parse(it) }
        }

        fun getHuaMahjong(): List<MahjongType> {
            return (41..48).mapNotNull { parse(it) }
        }
    }
}

/**
 * 麻将实体
 */
data class Mahjong(
    val id: Int, // 一副牌中每一张牌都有唯一标识，用于区别4张一万
    val type: MahjongType,
)