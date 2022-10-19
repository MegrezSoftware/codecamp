package com.example.myapplication

import androidx.annotation.IntRange

val numberMap = mapOf(1 to "一", 2 to "二", 3 to "三", 4 to "四", 5 to "五", 6 to "六", 7 to "七", 8 to "八", 9 to "九")
val nameMap = mapOf(1 to "萬", 2 to "筒", 3 to "条")

data class MaJiang(
    @IntRange(from = 1, to = 3)
    val type: Int,
    @IntRange(from = 1, to = 9)
    val value: Int
) {

    fun id(): Int {
        return (type - 1) * 10 + value
    }

    fun valueStr(): String {
        return "${numberMap[value]}${nameMap[type]}"
    }

}

data class MaJiangWrapper(
    val datas: List<MaJiang>,
    val ting: Boolean
)


fun createTestDatas(): List<MaJiangWrapper> {
    val case1 = MaJiangWrapper(
        datas = listOf(
            MaJiang(1, 1),
            MaJiang(1, 1),
            MaJiang(1, 1),
            MaJiang(1, 2),
            MaJiang(1, 2),
            MaJiang(1, 2),
            MaJiang(1, 3),
            MaJiang(1, 3),
            MaJiang(1, 3),
            MaJiang(1, 4),
            MaJiang(1, 4),
            MaJiang(1, 4),
            MaJiang(1, 5),
        ),
        ting = true
    )

    val case2 = MaJiangWrapper(
        datas = listOf(
            MaJiang(1, 1),
            MaJiang(1, 1),
            MaJiang(1, 2),
            MaJiang(1, 2),
            MaJiang(1, 3),
            MaJiang(1, 3),
            MaJiang(1, 4),
            MaJiang(1, 4),
            MaJiang(1, 5),
            MaJiang(1, 5),
            MaJiang(1, 6),
            MaJiang(1, 6),
            MaJiang(1, 7),
        ),
        ting = true
    )

    val case3 = MaJiangWrapper(
        datas = listOf(
            MaJiang(1, 1),
            MaJiang(1, 3),
            MaJiang(1, 5),
            MaJiang(1, 7),
            MaJiang(1, 9),
            MaJiang(2, 1),
            MaJiang(2, 3),
            MaJiang(2, 5),
            MaJiang(2, 7),
            MaJiang(2, 9),
            MaJiang(3, 1),
            MaJiang(3, 3),
            MaJiang(3, 5),
        ),
        ting = false
    )

    val case4 = MaJiangWrapper(
        datas = listOf(
            MaJiang(1, 1),
            MaJiang(1, 2),
            MaJiang(1, 3),
            MaJiang(1, 5),
            MaJiang(1, 7),
            MaJiang(2, 1),
            MaJiang(2, 2),
            MaJiang(2, 3),
            MaJiang(2, 9),
            MaJiang(2, 9),
            MaJiang(3, 1),
            MaJiang(3, 3),
            MaJiang(3, 2),
        ),
        ting = true
    )

    val case5 = MaJiangWrapper(
        datas = listOf(
            MaJiang(1, 1),
            MaJiang(1, 2),
            MaJiang(1, 2),
            MaJiang(1, 2),
            MaJiang(1, 2),
            MaJiang(2, 1),
            MaJiang(2, 2),
            MaJiang(2, 3),
            MaJiang(2, 9),
            MaJiang(2, 9),
            MaJiang(3, 1),
            MaJiang(3, 3),
            MaJiang(3, 2),
        ),
        ting = true
    )

    return mutableListOf<MaJiangWrapper>().apply {
        add(case1)
        add(case2)
        add(case3)
        add(case4)
        add(case5)
    }

}