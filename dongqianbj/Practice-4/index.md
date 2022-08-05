```
/** 枚举花色 */
enum Color = [
    WAN,
    TONG,
    TIAO,
    ZI,
    HUA
]

/** 枚举花色 */
enum Value = [
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE
]

/** 牌表 */
INITIAL_CONFIG = {
    WAN {
        ONE * 4
        TWO * 4
        THREE * 4
        FOUR * 4
        FIVE * 4
        SIX * 4
        SEVEN * 4
        EIGHT * 4
        NINE * 4
    }

    TONG {
        ONE * 4
        TWO * 4
        THREE * 4
        FOUR * 4
        FIVE * 4
        SIX * 4
        SEVEN * 4
        EIGHT * 4
        NINE * 4
    }

    TIAO {
        ONE * 4
        TWO * 4
        THREE * 4
        FOUR * 4
        FIVE * 4
        SIX * 4
        SEVEN * 4
        EIGHT * 4
        NINE * 4
    }

    ZI {
        ONE(DONG) * 4
        TWO(XI) * 4
        THREE(NAN) * 4
        FOUR(BEI) * 4
        FIVE(ZHONG) * 4
        SIX(FA) * 4
        SEVEN(BAI) * 4
    }

    HUA {
        ONE(CHUN) * 1
        TWO(XIA) * 1
        THREE(QIU) * 1
        FOUR(DONG) * 1
        FIVE(MEI) * 1
        FIVE(LAN) * 1
        SIX(ZHU) * 1
        SEVEN(JU) * 1
    }
}

/** 牌 */
Card {
    /** 花色 */
    color: Color

    /** 牌值 */
    value: Value

    /** 是否是宝牌 */
    is_gen: Boolean

    /** 是否是万能牌 */
    is_super: Boolean
}

/** 牌池 */
Pool {
    /** 牌池初值 */
    initial_queue = Card[]

    /** 牌池队列 */
    queue: Card[]

    /** 洗牌 */
    suffle = (pool: Pool) => void
}

/** 弃牌队列 */
Fold {
    /** 弃牌初值 */
    initial_queue = []

    /** 弃牌队列 */
    queue: Card[]

    /** 重置 */
    reset = () => void
}

/** 手牌表 */
Hand {
    /** 手牌初值 */
    initial_list = []

    /** 手牌表 */
    list: Card[]
}

/** 玩家 */
Player {
    /** 手牌表 */
    hand: Hand

    /** 下一个玩家 */
    next_player: Player
}

// 规则
Rule {
    /** 初始化手牌 */
    static init_hand = (pool: Pool) => Card[]

    /** 随机宝牌 */
    static pick_gem = (pool: Pool) => Card[]

    /** 设定万能牌 */
    static set_super = () => void

    /** 进牌：摸 */
    static in_touch = (pool: Pool) => Card

    /** 进牌：吃 */
    static in_eat = (fold: Fold) => Card

    /** 进牌：碰 */
    static in_crash = (fold: Fold) => Card

    /** 进牌：杠 */
    static in_aluba = (fold: Fold) => Card

    /** 出牌：弃 */
    static out_fold = (hand: Hand) => void

    /** 胡牌 */
    static check_win = (hand: Hand) => Boolean
}

function padding (pool, fold, player) {
    try {
        Rule.in_crash(player.hand)
        then
        Rule.in_eat(player.hand)
        then
        Rule.in_aluba(player.hand)
    }
    try_success {
        if (Rule.check_win(player.hand)) {
            return
        } else {
            Rule.out_fold(player.hand)
            process(pool, fold, player.next)
        }
    }
    try_fail {
        return
    }
}

function process (pool, fold, player) {
    try {
        padding(pool, fold, player.next)
        padding(pool, fold, player.next.next)
        padding(pool, fold, player.next.next.next)
    }
    try_success {
        return
    }
    try_fail {
        Rule.in_touch(player.hand)
        process(pool, fold, player)
    }
}

/** 初始化房间 */
config = INITIAL_CONFIG
pool = new Pool
Rule.set_super()
fold = new Fold

/** 初始化玩家 */
player1 = new Player()
player2 = new Player()
player3 = new Player()
player4 = new Player()

/** 开玩，此处循环 */
fold.reset()
pool.suffle()
Rule.pick_gem(pool)
process(pool, fold, player)
```