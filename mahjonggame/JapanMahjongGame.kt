package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 *
 */
class JapanMahjongGame : IMahjongGame {

    private val baseMahjongGame = BaseMahjongGame()

    /**
     * 没有花牌
     */
    override fun getSupportCard(): Map<MahjongType, Int> {
        return baseMahjongGame.getSupportCard() +
                MahjongType.getZiMahjong().associateWith { 4 }
    }

    /**
     * 无番不能胡
     */
    override fun judgeHu(cards: List<Mahjong>): Boolean {
        TODO("Not yet implemented")
    }

    override fun getGameFlow(): List<IStep<*, *>> {
        return baseMahjongGame.getGameFlow()
    }

    override fun calculateScore(cards: List<Mahjong>): Int {
        TODO("Not yet implemented")
    }
}