package com.tangtang.practice1


class HandTile(data: List<Mahjong>) {
    private val characters = mutableListOf<Character>()
    private val circles = mutableListOf<Circle>()
    private val bamboos = mutableListOf<Bamboo>()

    init {
        data.forEach {
            when (it) {
                is Character -> characters.add(it)
                is Circle -> circles.add(it)
                is Bamboo -> bamboos.add(it)
            }
        }
        characters.sortBy { it.value }
        circles.sortBy { it.value }
        bamboos.sortBy { it.value }
    }

    private fun check7Pairs(readies: MutableSet<Mahjong>) {
        val list = characters + circles + bamboos
        val set = mutableSetOf<Mahjong>()
        list.forEach { m ->
            set.find {
                m.value == it.value && m.type == it.type
            }?.run {
                set.remove(m)
            } ?: run {
                set.add(m)
            }
        }
        if (set.size == 1) {
            readies.addAll(set)
        }
    }

    private fun MutableSet<Mahjong>.filter2List(): List<Mahjong> {
        val list = characters + circles + bamboos
        val unavailableSet = mutableSetOf<Mahjong>()
        forEach { m ->
            if (list.count { m.value == it.value && m.type == it.type } > 3) {
                unavailableSet.add(m)
            }
        }
        removeAll(unavailableSet)
        return toList()
    }

    fun checkReady(): List<Mahjong> {
        val readies = mutableSetOf<Mahjong>()
        checkByPair(characters, circles, bamboos, readies)
        checkByPair(circles, bamboos, characters, readies)
        checkByPair(bamboos, characters,circles,  readies)
        check7Pairs(readies)
        return readies.filter2List()
    }

    private fun checkByPair(
            hasPairList: MutableList<out Mahjong>,
            otherList1: MutableList<out Mahjong>,
            otherList2: MutableList<out Mahjong>,
            readies: MutableSet<Mahjong>
    ) {
        val isOtherList1Match = matchStraightOrSet(otherList1)
        val isOtherList2Match = matchStraightOrSet(otherList2)

        hasPairList.forEachIndexed { index, mahjong ->
            val noPairList = hasPairList.toMutableList()
            // as second one of pair (that is , win by this mahjong)
            noPairList.remove(mahjong)
            if (isOtherList1Match && isOtherList2Match && matchStraightOrSet(noPairList)) {
                readies.add(mahjong)
            } else {
                // as first one of pair (that is , win by straight or set)
                if (isPairExist(hasPairList, index)) {
                    noPairList.remove(mahjong)

                    val isNoPairListMatch = matchStraightOrSet(noPairList)
                    val checkList = findCheckList(listOf(
                            isNoPairListMatch to noPairList,
                            isOtherList1Match to otherList1,
                            isOtherList2Match to otherList2
                    ))
                    if (checkList != null) {
                        checkByStraightOrSet(checkList, readies)
                    }
                }
            }
        }
    }

    private fun findCheckList(list: List<Pair<Boolean, List<Mahjong>>>): List<Mahjong>? {
        val isMatch = list.count { it.first } == 2
        return if (isMatch) list.firstOrNull { it.first.not() }?.second else null
    }

    fun win(tile: Mahjong): Boolean {
        TODO()
    }

    private fun matchStraightOrSet(tiles: List<Mahjong>): Boolean {
        if (tiles.size % 3 != 0) return false

        tiles.chunked(3).forEach {
            val v0 = it[0].value
            val v1 = it[1].value
            val v2 = it[2].value
            val isSet = (v0 == v1 && v1 == v2)
            val isStraight = v0 + 1 == v1 && v1 + 1 == v2
            if (!isSet && !isStraight) {
                return false
            }
        }
        return true
    }


    private fun checkByStraightOrSet(checkList: List<Mahjong>, readies: MutableSet<Mahjong>) {
        if (checkList.size % 3 != 2) return

        val count = checkList.size / 3
        for (x in 0..count) {
            val list = checkList.toMutableList()
            val v0 = checkList[x * 3]
            val v1 = checkList[x * 3 + 1]
            val isReadyStraightOrSet = v0.value == v1.value || v0.value + 1 == v1.value
            if (isReadyStraightOrSet) {
                list.remove(v0)
                list.remove(v1)
                if (matchStraightOrSet(list)) {
                    if (v0.value == v1.value) {
                        readies.add(v0)
                    } else {
                        v0.previous()?.let {
                            readies.add(it)
                        }
                        v1.next()?.let {
                            readies.add(it)
                        }
                    }
                }
            }
        }
    }

    private fun isPairExist(list: List<Mahjong>, index: Int): Boolean {
        return (index >= 0 && index + 1 < list.size && list[index] == list[index + 1])
    }
}