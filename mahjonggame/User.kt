package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 */
/**
 * 用户
 */
data class User(
    val id: Long,
    val nickName: String
)

/**
 * 玩家，包含手牌
 */
data class Player(
    val user: User,
    val handCards: MutableList<MahjongType> // 手牌
)