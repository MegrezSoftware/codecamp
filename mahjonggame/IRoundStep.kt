package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 *
 */
interface IRoundStep<I, O> : IStep<I, O>

data class TouchCardInput(
    val player: Player,
    val remainCards: List<Mahjong>, // 牌堆
)

data class TouchCardOutput(
    val player: Player,
    val remainCards: List<Mahjong>, // 牌堆
    val discardCard: Mahjong, // 弃牌
)

/**
 * 摸牌
 */
class TouchCards : IRoundStep<TouchCardInput, TouchCardOutput> {
    override fun operate(input: TouchCardInput): TouchCardOutput {
        TODO("Not yet implemented")
    }
}

data class EatBumpCardInput(
    val discardCard: Mahjong, // 上家弃牌
    val nextPlayer: Player, // 下家
)

/**
 * 吃
 */
class EatCards : IRoundStep<EatBumpCardInput, Boolean> {
    override fun operate(input: EatBumpCardInput): Boolean {
        TODO("Not yet implemented")
    }
}

/**
 * 碰
 */
class BumpCards : IRoundStep<EatBumpCardInput, Boolean> {
    override fun operate(input: EatBumpCardInput): Boolean {
        TODO("Not yet implemented")
    }
}