package mahjonggame

/**
 * Created by muchuanxin on 2022-08-17
 * 麻将游戏工厂
 */
class MahjongGameFactory {

    /**
     * 根据类型创建对应的麻将游戏
     */
    fun createMahjongGame(mahjongGameType: MahjongGameType): IMahjongGame {
        return when (mahjongGameType) {
            MahjongGameType.CHUAN -> ChuanMahjongGame()
            MahjongGameType.JING -> JingMahjongGame()
            MahjongGameType.JAPAN -> JapanMahjongGame()
        }
    }
}