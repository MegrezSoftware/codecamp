package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 *
 */
fun main() {
    val mahjongGame: IMahjongGame = TODO()
    val players: List<Player> = TODO()
    mahjongGame.getGameFlow().forEach {
        if (it is ILoopStep) {
            while (!it.isOver(it.operate(TODO()))) {
                // nothing
            }
        } else {
            it.operate(TODO())
        }
    }
}