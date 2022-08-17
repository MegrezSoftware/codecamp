package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 * 游戏入口
 */
class GameEntrance {

    /**
     * 开始游戏
     */
    fun startGame(players: List<Player>, mahjongGameType: MahjongGameType) {
        val mahjongGame: IMahjongGame = MahjongGameFactory().createMahjongGame(mahjongGameType)
        mahjongGame.getGameFlow().forEach {
            it.operate(TODO())
        }
    }
}