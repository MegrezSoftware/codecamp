package com.yuanfudao.megrez.app.codecamp.first

import java.util.*

/**
 * Created by lei.jialin on 2022/7/13
 */
class MatchResolver<T>(private val list: List<T>) where T : Mahjong {

    init {
    }

    fun calcOnSameGroup(): ArrayList<List<Match>> {
        val input: List<T> = list
        val treeMap = TreeMap<Int, Int>()
        input.forEach { mahjong ->
            treeMap[mahjong.num] = treeMap.getOrDefault(mahjong.num, 0) + 1
        }
        val answer = ArrayList<List<Match>>()
        if (input.isEmpty()) return answer

        findNextIndex(
            depth = 0,
            matchs = LinkedList<Match>(),
            cardBaseOn = input.first().num,
            remainMap = treeMap,
            answer = answer
        )
        answer.forEach {
            Logg.debugLn("结果：$it")
        }
        return answer
    }


    private fun TreeMap<Int, Int>.thirdLargerThan(number: Int): List<Int> {
        return this.keys.filter { cardNum -> (this[cardNum] ?: 0) > 0 && cardNum >= number }.take(3)
    }

    private fun TreeMap<Int, Int>.remainOne(): Boolean {
        return this.values.sum() <= 1
    }

    private fun findNextIndex(
        depth: Int,
        matchs: LinkedList<Match>,
        cardBaseOn: Int?,
        remainMap: TreeMap<Int, Int>,
        answer: ArrayList<List<Match>>
    ) {
        Logg.debugLn("- 递归：baseOn:$cardBaseOn, depth=$depth, matchs= $matchs, remainMap= $remainMap")
        if (list.size == depth || remainMap.remainOne()) {
            println("- 找到一组可能：baseOn:$cardBaseOn, matchs= $matchs, remainMap= $remainMap")
            answer.add(matchs)
            return
        }
        cardBaseOn ?: return
        val largerList = remainMap.thirdLargerThan(cardBaseOn)
        Logg.debugLn("- baseOn:$cardBaseOn, nextCard= $largerList, remainMap= $remainMap")
        largerList.forEach { nextCard ->
            val mahjong = nextCard
                ?.run { toMahjong(list.first()::class.java, this) }
                ?: return
            val lasMatch: Match? = matchs.peekLast()
            var isSingle = false
            kotlin.runCatching {
                if (lasMatch == null || lasMatch.length > 3) throw DiscardExcepetion("no need to merge last")
                val merge = (lasMatch.mahjongList + mahjong)
                MatchMaker.makeMatch(merge).apply {
                    if (this == lasMatch || this.level <= lasMatch.level) {
                        throw  DiscardExcepetion("no need to merge last")
                    }
                }
            }.apply { printError() }
                .onFailure {
                    isSingle = true
                    matchs.offerLast(Match.SingleOne(Card(mahjong = mahjong)))
                    Logg.debugLn("- 单张牌 ${mahjong.num}，上一个 $lasMatch 无法合并")
                }.onSuccess { merge ->
                    matchs.pollLast()
                    matchs.offerLast(merge)
                    Logg.debugLn("- 合并上一个 $lasMatch + $mahjong = ${merge}")
                }

            val old = remainMap.getOrDefault(mahjong.num, 0)
            remainMap[mahjong.num] = Math.max(0, old - 1)

            Logg.debugLn("- 牌 ${mahjong} 还剩下 ${old - 1}个， matchs: $matchs, lasMatch: $lasMatch, nextCard:$nextCard")
            findNextIndex(depth + 1, matchs, nextCard, remainMap, answer)

            val thisMatch = matchs.pollLast()
            if (!isSingle) {
                lasMatch?.let { matchs.offerLast(it) }
            }
            remainMap[nextCard] = old
            Logg.debugLn("- 回溯到牌 ${mahjong} 还剩下 ${old}个，matchs: $matchs, lasMatch: $lasMatch, nextCard:$nextCard")
        }
    }

    private fun toMahjong(
        data: Class<*>,
        num: Int
    ): Mahjong? {
        return when (data) {
            Circle::class.java -> Circle.values().find { it.num == num }
            Line::class.java -> Line.values().find { it.num == num }
            Character::class.java -> Character.values().find { it.num == num }
            else -> null
        }
    }

    private fun <T> Result<T>.printError() {
        this.onFailure {
            if (it !is DiscardExcepetion) {
                it.printStackTrace()
            }
        }
    }

    data class DiscardExcepetion(val msg: String? = "", val throws: Throwable? = null) :
        java.lang.IllegalStateException(msg, throws)
}