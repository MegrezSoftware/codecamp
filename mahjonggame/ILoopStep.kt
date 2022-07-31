package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 *
 */
interface ILoopStep<I, O> : IStep<I, O> {
    fun isOver(input: O): Boolean
}

data class RoundInput(
    val players: List<Player>,
    val remainCards: List<Mahjong>,
    val currentPlayer: Player,
)

data class RoundOutput(
    val players: List<Player>,
    val remainCards: List<Mahjong>,
    val nextPlayer: Player,
    val huPlayer: Player?, // 不空代表有人胡
)

/**
 * 回合
 */
class Round(private val roundSteps: List<IRoundStep<*, *>>) : ILoopStep<RoundInput, RoundOutput> {
    override fun operate(input: RoundInput): RoundOutput {
        roundSteps.forEach {
            it.operate(TODO())
        }
        return TODO()
    }

    override fun isOver(input: RoundOutput): Boolean {
        return input.huPlayer != null
    }
}

