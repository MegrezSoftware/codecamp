package com.example.myapplication

fun main() {
    createTestDatas().forEach {
        println("require:${it.ting} --- ${check(it.datas.map { it.id() })}")
    }
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
    println("check13$sortedInput")
    if (sortedInput.size != 13)
        return emptySet()
    val bingoSet = mutableSetOf<Int>()
    findTriple(sortedInput).forEach { triple ->
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
    println("check10$sortedInput")
    if (sortedInput.size != 10)
        return emptySet()
    val bingoSet = mutableSetOf<Int>()
    findTriple(sortedInput).forEach { triple ->
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
    println("check7$sortedInput")
    if (sortedInput.size != 7)
        return emptySet()
    val bingoSet = mutableSetOf<Int>()
    findTriple(sortedInput).forEach { triple ->
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
    println("check4$sortedInput")
    if (sortedInput.size != 4)
        return emptySet()
    val bingoSet = mutableSetOf<Int>()
    findTriple(sortedInput).forEach { triple ->
        check1(exclude(sortedInput, delete = triple)).let { result ->
            bingoSet.addAll(result)
        }
    }
    findPair(sortedInput).forEach { pair ->
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
    println("check2$sortedInput")
    if (sortedInput.size != 2)
        return emptySet()
    val bingoSet = mutableSetOf<Int>()
    if (sortedInput[0] == sortedInput[1]) {
        bingoSet.add(sortedInput[0])
    }
    if ((sortedInput[0] + 1) == sortedInput[1]) {
        bingoSet.add(sortedInput[0] + 2)
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
    println("check1$sortedInput")
    if (sortedInput.size != 1)
        return emptySet()
    return setOf(sortedInput[0])
}

/**
 * 顺或者刻
 */
fun findTriple(sortedInput: List<Int>): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    var index = 0
    while (index < sortedInput.size - 2) {
        //刻
        if (sortedInput[index] == sortedInput[index + 1] && sortedInput[index] == sortedInput[index + 2]) {
            result.add(listOf(sortedInput[index], sortedInput[index + 1], sortedInput[index + 2]))
            index += 3
        } else {
            index++
        }
    }
    index = 0
    val distinctInput = sortedInput.distinct()
    while (index < distinctInput.size - 2) {
        //顺
        if ((distinctInput[index] + 1) == distinctInput[index + 1] && (distinctInput[index] + 2) == distinctInput[index + 2]) {
            result.add(listOf(distinctInput[index], distinctInput[index + 1], distinctInput[index + 2]))
            index += 3
        } else {
            index++
        }
    }
    return result
}

/**
 * 对
 */
fun findPair(sortedInput: List<Int>): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    var index = 0
    while (index < sortedInput.size - 1) {
        if (sortedInput[index] == sortedInput[index + 1]) {
            result.add(listOf(sortedInput[index], sortedInput[index + 1]))
        }
        index++
    }
    return result
}

/**
 * 集合相减
 */
fun exclude(sortedInput: List<Int>, delete: List<Int>): List<Int> {
    println("sortedInput${sortedInput}")
    println("delete${delete}")
    val result = sortedInput.toMutableList()
    return delete.fold(result) { acc, cur ->
        val deleteIndex = acc.indexOf(cur)
        if (deleteIndex in acc.indices) {
            acc.removeAt(deleteIndex)
        }
        acc
    }
}
