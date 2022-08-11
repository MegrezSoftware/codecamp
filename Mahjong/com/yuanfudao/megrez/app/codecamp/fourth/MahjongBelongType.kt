package com.yuanfudao.megrez.app.codecamp.fourth
enum class MahjongBelongType(
    val shortName: String,
    val internalOffset: Int,  // 用于更好定位
) {
    Wan("万", 0),
    Circle("筒", 10),
    Line("条", 20),
    Chars("字牌", 30),
}
