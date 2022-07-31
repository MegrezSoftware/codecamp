package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 *
 */
interface IMahjongGame {
    /**
     * @return 支持的牌的种类，和每种牌对应的个数
     */
    fun getSupportCard(): Map<MahjongType, Int>

    /**
     * 判胡
     */
    fun judgeHu(cards: List<Mahjong>): Boolean

    /**
     * 游戏流程
     */
    fun getGameFlow(): List<IStep<*, *>>

    /**
     * 计分，取最大
     */
    fun calculateScore(cards: List<Mahjong>): Int
}