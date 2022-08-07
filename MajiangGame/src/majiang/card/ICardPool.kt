package majiang.card

/**
 * 麻将牌堆，每局麻将的牌组。
 */
interface ICardPool {
    /**
     * 是否还有牌
     */
    fun hasCard(): Boolean

    /**
     * 取牌，随机获取一张牌。取牌前需判断是否还有牌
     * @throws NoCardException 如果没有牌，则抛出该异常
     */
    fun requestCard(): MajiangCard
}

class NoCardException() : Exception()
