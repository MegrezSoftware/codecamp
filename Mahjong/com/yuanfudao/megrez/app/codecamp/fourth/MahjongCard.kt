package com.yuanfudao.megrez.app.codecamp.fourth

data class MahjongCard(
    val num: Int,
    val belongCardType: MahjongBelongType,
    val innerPresentIndex: Int = num + belongCardType.internalOffset,
) {

}
