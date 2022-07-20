package com.yuanfudao.megrez.app.codecamp.first

import java.util.*

class WhichToWin(
    private val circle: Candidate?,
    private val line: Candidate?,
    private val character: Candidate?
) {
    private val alreadyDetermine = BooleanArray(9) { false }

    init {
        initAnalyzing()
    }

    private fun initAnalyzing() {
        // TODO: 合并三种牌的结果，现在是只支持单个种类的牌输入
//        Candidate(match = LinkedList<List<Match>>().apply {
//            circle?.match?.let { add(it) }
//            line?.match?.let { add(it) }
//            character?.match?.let { add(it) }
//        })
    }

    private fun analyzeCandidate(candidate: Candidate): List<Int> {
        val calcInfo = calculateMissingAccount(candidate)
        calcInfo.judgeWhichToWin(candidate, alreadyDetermine)
        val worthWaiting = ArrayList<Int>()
        alreadyDetermine.forEachIndexed { index: Int, canWait: Boolean ->
            // card index start from 0, so need +1
            if (canWait) worthWaiting.add(index + 1)
        }
        return worthWaiting
    }

    private fun calculateMissingAccount(candidate: Candidate): CalcMissingInfo {
        val missing = HashSet<Int>()
        val doubles = LinkedList<Match.Doubles>()
        val twoSibling = LinkedList<Match.TwoSibling>()
        val single = LinkedList<Match.SingleOne>()
        val threes = LinkedList<Match>()
        val next2Next = LinkedList<Match.Next2Next>()
        var cardCount = 0
        kotlin.runCatching {
            candidate.match.forEach { match ->
                when (match) {
                    is Match.SingleOne -> {
                        single.add(match)
                    }
                    is Match.Next2Next -> {
                        next2Next.add(match)
                    }
                    is Match.Doubles -> {
                        doubles.add(match)
                    }
                    is Match.TwoSibling -> {
                        twoSibling.add(match)
                    }
                    is Match.None -> {
                    }
                    else -> {
                        if (match.length > 2) {
                            threes.add(match)
                        }
                    }
                }
                cardCount += match.length
            }
        }.onFailure {
            Logg.debugLn(it.message ?: "")
        }
        return CalcMissingInfo(
            missing = missing,
            doubles = doubles,
            single = single,
            threes = threes,
            cardCount = cardCount,
            twoSibling = twoSibling,
            next2Next = next2Next,
        )
    }

    private fun CalcMissingInfo.judgeWhichToWin(
        candidate: Candidate,
        alreadyDetermine: BooleanArray,
    ): List<Int> {
        var remain: Mahjong? = candidate.remainCard
        val onlyPair = kotlin.runCatching {
            if (doubles.isEmpty()) {
                if (single.size == 1 && single.first().card.num == remain?.num) {
                    remain = null
                    single.first.also { single.remove(it) }
                } else throw DiscardException(" --- 没找到唯一的对儿，只有两张单牌：${single.first}, ${remain?.num}")
            } else if (doubles.size == 1) {
                doubles.first.also {
                    doubles.remove(it)
                }
            } else if (doubles.size == 6 && doubles.find { it.card.num == remain?.num } == null && remain != null) {
                val take = remain
                remain = null
                take
            } else if (doubles.size == 2 && doubles.find { it.card.num == remain?.num } != null && remain != null) {
                // become three Card
                doubles.removeIf { it.card.num == remain?.num }
                remain = null

                doubles.first.also {
                    doubles.remove(it)
                }
            } else {
                throw  DiscardException(" --- 没找到唯一的对儿，太多不符合的两对：多余的对儿 = ${doubles}, 单牌：${single}，剩余单牌：${remain?.num}")
            }
        }.onFailure {
            Logg.debugLn(it.message ?: "")
        }.getOrNull()
        next2Next.takeIf { remain != null }
            ?.find { it.missing == remain?.num }
            ?.let { replace ->
                next2Next.remove(replace)
                remain = null
            }
        val waitingCard = kotlin.runCatching {
            if (onlyPair == null) {
                throw DiscardException("没有对儿")
            } else if ((next2Next.size + doubles.size + twoSibling.size
                        + single.size + (if (remain != null) 1 else 0)) > 1
            ) {
                throw DiscardException("没有这么多牌给填上空缺")
            } else if (twoSibling.size == 1) {
                requireNotNull(twoSibling.first.run {
                    val prev = candidate.stillOutThere(first.num - 1)
                    val next = candidate.stillOutThere(second.num + 1)
                    if (prev && next) {
                        listOf(first.num - 1, second.num + 1)
                    } else if (prev) {
                        listOf(first.num - 1)
                    } else if (next) {
                        listOf(second.num + 1)
                    } else throw DiscardException(" --- ${twoSibling.first} 均取不到两个边界为听牌")
                })
            } else if (doubles.size == 1) {
                listOf(doubles.first().card.num)
            } else if (next2Next.size == 1) {
                listOf(next2Next.first().missing)
            } else if (single.size == 1) {
                listOf(single.first().card.num)
            } else throw DiscardException(" --- 缺牌太多：$missing， $candidate")
        }.onFailure {
            Logg.debugLn("// 听牌失败 // - 原因：${it.message}, 根据目前手牌：${candidate.match}, 和剩余的一张牌：${remain}, 唯一的对儿: $onlyPair, 还缺失的牌中间记录：${missing.toList()}")
        }.getOrDefault(emptyList())

        val environment =
            "根据目前手牌：${candidate.match}, 和剩余的一张牌：${remain}, 唯一的对儿: $onlyPair, 还缺失的牌中间记录：${missing.toList()}\n            - 中间结果，听牌：${next2Next}。doubles=$doubles，twoSibling=$twoSibling, single=$single,next2Next=$next2Next, remain=$remain "

        when (waitingCard.size) {
            0 -> {
                Logg.debugLn("// 听牌失败 // - 没有听牌。$environment")
            }
            1 -> {
                Logg.combineLn("【 听牌成功 】 - 听牌：${waitingCard.first()}。$environment")
                alreadyDetermine[waitingCard.first() - 1] = true
            }
            else -> {
                Logg.combineLn("【 多张听牌成功 】 - 听牌：${waitingCard.joinToString("或")}。$environment")
                waitingCard.forEach {
                    alreadyDetermine[it - 1] = true
                }
            }
        }
        return waitingCard
    }

    private fun Candidate.stillOutThere(targetNumber: Int): Boolean {
        return targetNumber in 1..9 && (4 - countMap.getOrDefault(targetNumber, 0) > 0)
    }

    fun getWhichToWinList(): List<Mahjong> {
        val result = ArrayList<Mahjong>()
        result.addAll(circle?.run { analyzeCandidate(this).asMahjong(this.inputSubSet) }
            ?: emptyList())
        result.addAll(line?.run { analyzeCandidate(this).asMahjong(this.inputSubSet) }
            ?: emptyList())
        result.addAll(character?.run { analyzeCandidate(this).asMahjong(this.inputSubSet) }
            ?: emptyList())
        return result
    }

    private inline fun <reified T> List<Int>?.asMahjong(sameType: List<T>): List<T> where T : Mahjong {
        val tingCards = this
        if (tingCards.isNullOrEmpty()) return emptyList()
        val first = sameType.first()
        val findFrom = when (first) {
            is Circle -> Circle.values()
            is Line -> Line.values()
            is Character -> Character.values()
            else -> emptyArray()
        }
        return tingCards.mapNotNull { cardNum -> findFrom.find { it.num == cardNum } as? T }
    }
}