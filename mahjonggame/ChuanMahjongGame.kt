package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 *
 */
class ChuanMahjongGame : IMahjongGame {

    private val baseMahjongGame = BaseMahjongGame()

    override fun getSupportCard(): Map<MahjongType, Int> {
        return baseMahjongGame.getSupportCard() +
                MahjongType.getZiMahjong().associateWith { 4 } +
                MahjongType.getHuaMahjong().associateWith { 1 }
    }

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