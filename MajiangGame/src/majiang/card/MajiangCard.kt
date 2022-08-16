package majiang.card

data class MajiangCard(val type: MajiangCardType)

/**
 * 麻将牌类型枚举，省略了很多牌的类型
 * @property id 牌的id
 */
enum class MajiangCardType(val id: Int) {
    /**
     * 一万
     */
    YI_WAN(1),

    /**
     * 一桶
     */
    YI_TONG(11),

    /**
     * 一条
     */
    YI_TIAO(21),

    /**
     * 东风
     */
    DONG_FENG(31),

    /**
     * 花牌：春
     */
    CHUN(41),
}