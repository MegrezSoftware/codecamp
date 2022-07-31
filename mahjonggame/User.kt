package mahjonggame

/**
 * Created by muchuanxin on 2022-07-31
 *
 */
data class User(
    val id: Long,
    val nickName: String
)

data class Player(
    val user: User,
    val handCards: MutableList<MahjongType> // 手牌
)