package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 *
 */
class BaseMahjongGame : IMahjongGame {
    override fun getSupportCard(): Map<MahjongType, Int> {
        return MahjongType.getBaseMahjong().associateWith { 4 }
    }

    override fun judgeHu(cards: List<Mahjong>): Boolean {
        TODO("Not yet implemented")
    }

    override fun getGameFlow(): List<IStep<*, *>> {
        return listOf(WashCards(), FirstTouchCards(), getBaseRound())
    }

    override fun calculateScore(cards: List<Mahjong>): Int {
        TODO("Not yet implemented")
    }

    private fun getBaseRound(): Round {
        return Round(listOf(TouchCards(), EatCards(), BumpCards()))
    }
}