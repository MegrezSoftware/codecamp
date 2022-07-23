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
            return values().first { it.number == number }
        }
    }

    fun maxCount(): Int {
        return 1
    }

    fun computeValue(): Int {
        return number * 1
    }
}

enum class MajiangType(val displayName: String, private val number: Int) {
    NULL("", 0),
    WAN("万", 1),
    TONG("筒", 2),
    TIAO("条", 3);

    companion object {
        fun parse(number: Int): MajiangType {
            return values().first { it.number == number }
        }
    }


    fun maxCount(): Int {
        return if (this == NULL) 1 else 4
    }

    fun computeValue(): Int {
        return number * 10
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
            return values().first { it.number == number }
        }
    }


    fun maxCount(): Int {
        return if (this == NULL) 1 else 4
    }

    fun computeValue(): Int {
        return number * 100
    }
}

enum class PowerType(val displayName: String, private val number: Int) {
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
        fun parse(number: Int): PowerType {
            return values().first { it.number == number }
        }
    }

    fun maxCount(): Int {
        return 1
    }

    fun computeValue(): Int {
        return number * 1000
    }
}

data class MajiangCardItem(
    val number: MajiangNumber = MajiangNumber.ZERO,
    val type: MajiangType = MajiangType.NULL,
    val word: MajiangWord = MajiangWord.NULL,
    val power: PowerType = PowerType.NULL,
) {
    fun isValid(): Boolean {
        return (number != MajiangNumber.ZERO && type != MajiangType.NULL && word == MajiangWord.NULL && power == PowerType.NULL)
                || (number == MajiangNumber.ZERO && type == MajiangType.NULL && word == MajiangWord.NULL && power != PowerType.NULL)
                || (number == MajiangNumber.ZERO && type == MajiangType.NULL && word != MajiangWord.NULL && power == PowerType.NULL)
    }

    fun maxCount(): Int {
        return number.maxCount() * type.maxCount() * word.maxCount() * power.maxCount()
    }

    fun value(): Int {
        return number.computeValue() + type.computeValue() + power.computeValue()
    }

    override fun toString(): String {
        return "${number.displayName}${type.displayName}${word.displayName}${power.displayName}"
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
                        PowerType.values().map { power ->
                            MajiangCardItem(number, type, word, power)
                        }
                    }
                }
            }.filter { it.isValid() }
            return MajiangCardGroup(cards)
        }

        fun empty(): MajiangCardGroup {
            return MajiangCardGroup(emptyList())
        }
    }

    fun excludePowerCardGroup(): MajiangCardGroup {
        return MajiangCardGroup(cardItems.filter { it.power == PowerType.NULL })
    }

    /**
     * 判断牌是否合法
     */
    fun isValid(): Boolean {
        if (!cardItems.all { it.isValid() }) return false
        val countMap = mutableMapOf<MajiangCardItem, Int>()
        cardItems.forEach {
            countMap[it] = (countMap[it] ?: 0) + 1
        }
        return cardItems.all { (countMap[it] ?: 0) <= it.maxCount() }
    }

    override fun toString(): String {
        return cardItems.joinToString(separator = " ") { it.toString() }
    }
}