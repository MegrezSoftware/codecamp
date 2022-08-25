package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 * 京麻游戏
 */
class JingMahjongGame : IMahjongGame {

    private val baseMahjongGame = BaseMahjongGame()

    /**
     * 没有花牌
     */
    override fun getSupportCard(): Map<MahjongType, Int> {
        return baseMahjongGame.getSupportCard() +
                MahjongType.getZiMahjong().associateWith { 4 }
    }

    /**
     * 指定混
     */
    override fun judgeHu(cards: List<Mahjong>): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * 不能吃
     */
    override fun getGameFlow(): List<IStep<*, *>> {
        return listOf(WashCards(), FirstTouchCards(), Round(listOf(TouchCards(), BumpCards())))
    }

    override fun calculateScore(cards: List<Mahjong>): Int {
        TODO("Not yet implemented")
    }
}