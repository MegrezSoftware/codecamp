## 思路

- 初始化牌桌
  - 确定规则
  - 确定牌表
  - 初始化牌池
  - 初始化弃牌队列
- 初始化玩家
  - 初始化玩家实例
- 初始化牌局
  - 重置牌池
  - 重置弃牌队列
  - 确定随机宝牌

## 伪代码

### 枚举牌色

```
enum Color = [
    WAN,
    TONG,
    TIAO,
    ZI,
    HUA
]
```

### 枚举牌值

```
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
```

### 牌表配置

```
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
```

### 牌的数据结构

```
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
```

### 牌池队列

```
Pool {
    /** 牌池初值 */
    initial_queue = Card[]

    /** 牌池队列 */
    queue: Card[]

    /** 洗牌 */
    suffle = (pool: Pool) => void
}
```

### 弃牌队列

```
Fold {
    /** 弃牌初值 */
    initial_queue = []

    /** 弃牌队列 */
    queue: Card[]

    /** 重置 */
    reset = () => void
}
```

### 手牌表

```
Hand {
    /** 手牌初值 */
    initial_list = []

    /** 手牌表 */
    list: Card[]
}
```

### 玩家

```
Player {
    /** 手牌表 */
    hand: Hand

    /** 下家 */
    next_player: Player

    /** 重置手牌表 */
    reset = () => void
}
```

### 规则

```
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
```

### 等待轮询

```
function padding (pool, fold, current_player, player) {
    try {
        current_player === player
    }
    try_success {
        return null
    }
    try_fail {
        try {
            Rule.in_crash(player.hand)
            or
            Rule.in_eat(player.hand)
            or
            Rule.in_aluba(player.hand)
        }
        try_success {
            try {
                Rule.check_win(player.hand)
            }
            try_success {
                return player
            }
            try_fail {
                // do nothing
            }
        }
        try_fail {
            return padding(pool, fold, current_player, player.next)
        }
    }
}
```

### 上牌

```
function process (pool, fold, player) {
    touch = Rule.in_touch(player.hand)

    try {
        Rule.check_aluba(touch)
    }
    try_success {
        process(pool, fold, player)
    }
    try_fail {
        try {
            Rule.check_win(player.hand)
        }
        try_success {
            return
        }
        try_fail {
            Rule.out_fold(player.hand)
            try {
                new_player = padding(pool, fold, current_player, player)
                not_null(new_player)
            }
            try_success {
                process(pool, fold, new_player)
            }
            try_fail {
                process(pool, fold, player.next)
            }
        }
    }
}
```

### 初始化牌桌

```
function init_table () {
    config = INITIAL_CONFIG
    pool = new Pool(config)
    fold = new Fold
    Rule.set_super()
}
```

### 初始化玩家

```
function init_player () {
    player = new Player()
}
```

### 初始化牌局

```
function init_game () {
    fold.reset()
    pool.suffle()
    Rule.pick_gem(pool)
    player.reset()
}
```

### 开始游戏
```
function start_game () {
    process(pool, fold, player)
}
```