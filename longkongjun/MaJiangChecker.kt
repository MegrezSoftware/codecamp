package com.example.myapplication

fun main() {
    val test = intArrayOf(1,2,3,4,5,6,7,8,9,11,12,13,14)
    println()
    println(check(test.toList()))
}


/**
 * 判断是否听牌
 * 1~9:萬
 * 11～19：筒
 * 21～29：条
 */
fun check(input: List<Int>): Boolean {
    val bingoSet = check13(input.sorted())
    return bingoSet.any { bingo ->
        input.count { it == bingo } < 4
    }
}

/**
 * 判断13张牌是否听牌
 */
fun check13(sortedInput: List<Int>): Set<Int> {
    if (sortedInput.size != 13)
        return emptySet()
    val bingoSet = mutableSetOf<Int>()
    sortedInput.windowed(size = 3).filter {
        checkTriple(it)
    }.forEach { triple ->
        check10(exclude(sortedInput, delete = triple)).let { result ->
            bingoSet.addAll(result)
        }
    }
    return bingoSet
}

/**
 * 判断10张牌是否听牌
 * @param sortedInput 排序过的牌
 * @return  可以听哪些牌
 */
fun check10(sortedInput: List<Int>): Set<Int> {
    if (sortedInput.size != 10)
        return emptySet()
    val bingoSet = mutableSetOf<Int>()
    sortedInput.windowed(size = 3).filter {
        checkTriple(it)
    }.forEach { triple ->
        check7(exclude(sortedInput, delete = triple)).let { result ->
            bingoSet.addAll(result)
        }
    }
    return bingoSet
}

/**
 * 判断7张牌是否听牌
 * @param sortedInput 排序过的牌
 * @return  可以听哪些牌
 */
fun check7(sortedInput: List<Int>): Set<Int> {
    if (sortedInput.size != 7)
        return emptySet()
    val bingoSet = mutableSetOf<Int>()
    sortedInput.windowed(size = 3).filter {
        checkTriple(it)
    }.forEach { triple ->
        check4(exclude(sortedInput, delete = triple)).let { result ->
            bingoSet.addAll(result)
        }
    }
    return bingoSet
}

/**
 * 判断4张牌是否听牌
 * @param sortedInput 排序过的牌
 * @return  可以听哪些牌
 */
fun check4(sortedInput: List<Int>): Set<Int> {
    if (sortedInput.size != 4)
        return emptySet()
    val bingoSet = mutableSetOf<Int>()
    sortedInput.windowed(size = 3).filter {
        checkTriple(it)
    }.forEach { triple ->
        check1(exclude(sortedInput, delete = triple)).let { result ->
            bingoSet.addAll(result)
        }
    }

    sortedInput.windowed(size = 2).filter {
        checkPair(it)
    }.forEach { pair ->
        check2(exclude(sortedInput, delete = pair)).let { result ->
            bingoSet.addAll(result)
        }
    }
    return bingoSet
}

/**
 * 判断2张牌是否听牌
 * @param sortedInput 排序过的牌
 * @return  可以听哪些牌
 */
fun check2(sortedInput: List<Int>): Set<Int> {
    if (sortedInput.size != 2)
        return emptySet()
    val bingoSet = mutableSetOf<Int>()
    if (sortedInput[0] == sortedInput[1]) {
        bingoSet.add(sortedInput[0])
    }
    if ((sortedInput[0] + 2) == sortedInput[1]) {
        bingoSet.add(sortedInput[0] + 1)
    }
    return bingoSet
}

/**
 * 判断1张牌是否听牌
 * @param sortedInput 排序过的牌
 * @return  可以听哪些牌
 */
fun check1(sortedInput: List<Int>): Set<Int> {
    if (sortedInput.size != 1)
        return emptySet()
    return setOf(sortedInput[0])
}

/**
 * 顺或者刻
 */
fun checkTriple(sortedInput: List<Int>): Boolean {
    if (sortedInput.size != 3)
        return false
    return (sortedInput[0] == sortedInput[1] && sortedInput[1] == sortedInput[2])
            || (((sortedInput[0] + 1) == sortedInput[1]) && ((sortedInput[1] + 1) == sortedInput[2]))
}

/**
 * 对
 */
fun checkPair(sortedInput: List<Int>): Boolean {
    if (sortedInput.size != 2)
        return false
    return (sortedInput[0] == sortedInput[1] && sortedInput[1] == sortedInput[2])
            || (((sortedInput[0] + 1) == sortedInput[1]) && ((sortedInput[1] + 1) == sortedInput[2]))
}

/**
 * 集合相减
 */
fun exclude(sortedInput: List<Int>, delete: List<Int>): List<Int> {
    val result = sortedInput.toMutableList()
    return delete.fold(result) { acc, cur ->
        val deleteIndex = acc.indexOf(cur)
        if (deleteIndex in acc.indices) {
            acc.removeAt(deleteIndex)
        }
        acc
    }
}
