package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 *
 */
interface IStep<I, O> {
    fun operate(input: I): O
}

/**
 * 洗牌
 */
class WashCards : IStep<Map<MahjongType, Int>, List<Mahjong>> {
    override fun operate(input: Map<MahjongType, Int>): List<Mahjong> {
        TODO("Not yet implemented")
    }
}

data class FirstTouchCardInput(
    val player: Player,
    val count: Int, // 摸牌数量
    val remainCards: List<Mahjong>, // 牌堆
)

data class FirstTouchCardOutput(
    val player: Player,
    val remainCards: List<Mahjong>, // 牌堆
)

/**
 * 首回合摸牌
 */
class FirstTouchCards : IStep<FirstTouchCardInput, FirstTouchCardOutput> {
    override fun operate(input: FirstTouchCardInput): FirstTouchCardOutput {
        TODO("Not yet implemented")
    }
}