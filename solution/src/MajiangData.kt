enum class MajiangNumber(val displayName: String, private val number: Int) {
    ZERO("", 0),
    FIRST("一", 1),
    SECOND("二", 2),
    THIRD("三", 3),
    FORTH("四", 4),
    FIFTH("五", 5),
    SIX("六", 6),
    SEVEN("七", 7),
    EIGHT("八", 8),
    JIU("九", 9);

    companion object {
        fun parse(number: Int): MajiangNumber {
            return values().firstOrNull { it.number == number } ?: ZERO
        }

        val weight = 1
    }


    fun maxCount(): Int {
        return 1
    }

    fun computeValue(): Int {
        return number * weight
    }
}

enum class MajiangType(val displayName: String, private val number: Int) {
    NULL("", 0),
    WAN("万", 1),
    TONG("筒", 2),
    TIAO("条", 3);

    companion object {
        fun parse(number: Int): MajiangType {
            return values().firstOrNull { it.number == number } ?: NULL
        }

        val weight = 10
    }


    fun maxCount(): Int {
        return if (this == NULL) 1 else 4
    }

    fun computeValue(): Int {
        return number * weight
    }
}

enum class MajiangWord(val displayName: String, private val number: Int) {
    NULL("", 0),
    DONG("东风", 1),
    XI("西风", 2),
    NAN("南风", 3),
    BEI("北风", 4),
    ZHONG("红中", 5),
    BAI("白板", 6),
    FA("发财", 7);

    companion object {
        fun parse(number: Int): MajiangWord {
            return values().firstOrNull { it.number == number } ?: NULL
        }

        val weight = 100
    }


    fun maxCount(): Int {
        return if (this == NULL) 1 else 4
    }

    fun computeValue(): Int {
        return number * weight
    }
}

enum class MajiangFlower(val displayName: String, private val number: Int) {
    NULL("", 0),
    CHUN("春", 1),
    XIA("夏", 2),
    QIU("秋", 3),
    DONG("冬", 4),
    MEI("梅", 5),
    LAN("兰", 6),
    ZHU("竹", 7),
    JU("菊", 8);

    companion object {
        fun parse(number: Int): MajiangFlower {
            return values().firstOrNull { it.number == number } ?: NULL
        }

        val weight = 1000
    }


    fun maxCount(): Int {
        return 1
    }

    fun computeValue(): Int {
        return number * weight
    }
}

data class MajiangCardItem(
    val number: MajiangNumber = MajiangNumber.ZERO,
    val type: MajiangType = MajiangType.NULL,
    val word: MajiangWord = MajiangWord.NULL,
    val flower: MajiangFlower = MajiangFlower.NULL,
) {
    companion object {
        fun parse(value: Int): MajiangCardItem {
            val number = MajiangNumber.parse((value / MajiangNumber.weight) % 10)
            val type = MajiangType.parse((value / MajiangType.weight) % 10)
            val word = MajiangWord.parse((value / MajiangWord.weight) % 10)
            val power = MajiangFlower.parse((value / MajiangFlower.weight) % 10)
            return MajiangCardItem(number, type, word, power)
        }
    }

    fun isValid(): Boolean {
        return (number != MajiangNumber.ZERO && type != MajiangType.NULL && word == MajiangWord.NULL && flower == MajiangFlower.NULL)
                || (number == MajiangNumber.ZERO && type == MajiangType.NULL && word == MajiangWord.NULL && flower != MajiangFlower.NULL)
                || (number == MajiangNumber.ZERO && type == MajiangType.NULL && word != MajiangWord.NULL && flower == MajiangFlower.NULL)
    }

    fun maxCount(): Int {
        return number.maxCount() * type.maxCount() * word.maxCount() * flower.maxCount()
    }

    fun value(): Int {
        return number.computeValue() + type.computeValue() + word.computeValue() + flower.computeValue()
    }

    override fun toString(): String {
        return "[${number.displayName}${type.displayName}${word.displayName}${flower.displayName}]"
    }
}

data class MajiangCardGroup(val cardItems: List<MajiangCardItem>) {
    companion object {
        /**
         * 所有类型的牌
         */
        fun allCards(): MajiangCardGroup {
            val cards = MajiangNumber.values().flatMap { number ->
                MajiangType.values().flatMap { type ->
                    MajiangWord.values().flatMap { word ->
                        MajiangFlower.values().map { power ->
                            MajiangCardItem(number, type, word, power)
                        }
                    }
                }
            }.filter { it.isValid() }
            return MajiangCardGroup(cards).sorted()
        }

        fun empty(): MajiangCardGroup {
            return MajiangCardGroup(emptyList())
        }
    }

    fun containsCardItem(item: MajiangCardItem): Boolean {
        return cardItems.find { it.value() == item.value() } != null
    }

    fun count(): Int = cardItems.count()

    fun addCardItem(vararg cardItem: MajiangCardItem): MajiangCardGroup {
        return MajiangCardGroup(cardItems.toMutableList().apply { addAll(cardItem) })
    }

    fun removeCardItem(vararg cardItem: MajiangCardItem): MajiangCardGroup {
        return MajiangCardGroup(cardItems.toMutableList().apply { removeAll(cardItem) })
    }

    fun sorted(): MajiangCardGroup {
        return MajiangCardGroup(cardItems.sortedBy { it.value() })
    }

    fun removeDuplicated(): MajiangCardGroup {
        return MajiangCardGroup(toCountMap().keys.toList())
    }

    fun toCountMap(): Map<MajiangCardItem, Int> {
        val countMap = mutableMapOf<Int, Int>()
        cardItems.forEach {
            val key = it.value()
            countMap[key] = (countMap[key] ?: 0) + 1
        }
        return countMap.map { Pair(MajiangCardItem.parse(it.key), it.value) }.toMap()
    }

    /**
     * 排除万能牌后的牌组
     */
    fun excludePowerCardGroup(): MajiangCardGroup {
        return MajiangCardGroup(cardItems.filter { it.flower == MajiangFlower.NULL })
    }

    /**
     * 只保留万能牌的牌组
     */
    fun extractPowerCardGroup(): MajiangCardGroup {
        return MajiangCardGroup(cardItems.filter { it.flower != MajiangFlower.NULL })
    }

    /**
     * 判断牌是否合法
     */
    fun isValid(): Boolean {
        if (!cardItems.all { it.isValid() }) return false
        val countMap = toCountMap()
        return cardItems.all { (countMap[it] ?: 0) <= it.maxCount() }
    }

    override fun toString(): String {
        return cardItems.joinToString(separator = " ") { it.toString() }
    }

    operator fun plus(group: MajiangCardGroup): MajiangCardGroup {
        return MajiangCardGroup(cardItems + group.cardItems)
    }

}