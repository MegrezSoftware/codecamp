/**
 * @(#)Solution1.java, 7月 15, 2022.
 *
 *
 * Copyright 2022 yuanfudao.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yuanfudao.megrez.app.codecamp.third

import java.lang.IllegalArgumentException
import java.util.ArrayList

object MahjongWaitingFinder {
    private val allCards =
        Wan.values().map { it.num + it.internalOffset } +
                Circle.values().map { it.num + it.internalOffset } +
                Line.values().map { it.num + it.internalOffset } +
                Chars.values().map { it.num + it.internalOffset }

    /**
     * @param cards 输入的麻将牌
     * @param arbitraryCount 输入的任意牌
     * @return 可以听的麻将牌
     *
     * @throws IllegalArgumentException 当arbitraryCount小于0时会抛出异常。
     * arbitraryCount和cards的个数之和必须等于13，否则抛出异常
     * */
    fun whichToWinMahjong(cards: List<Int>, arbitraryCount: Int): List<Mahjong> {
        return kotlin.runCatching {
            whichToWinUnderArbitraryOf(cards, arbitraryCount)
                .mapNotNull { it.innerIndexToMahjong() }
        }.onFailure { print(it.message + " -> ") }
            .getOrDefault(emptyList())
    }

    /**
     * @param cards 输入麻将牌的innerIndex
     * @param arbitraryCount 输入的任意牌
     * @return 可以听的麻将牌的innerIndex
     *
     * @throws IllegalArgumentException 当arbitraryCount小于0时会抛出异常。
     * arbitraryCount和cards的个数之和必须等于13，否则抛出异常
     * */
    private fun whichToWinUnderArbitraryOf(cards: List<Int>, arbitraryCount: Int): List<Int> {
        if (arbitraryCount < 0) throw IllegalArgumentException("输入任意牌数量不能小于0")
        else if (arbitraryCount + cards.size != 13) throw IllegalArgumentException("输入手牌不足13")
        val waitingCard: MutableList<Int> = ArrayList()
        val maxCardInnerIndex = allCards.lastOrNull() ?: 0
        val remain = IntArray(maxCardInnerIndex + 1)
        cards.forEach { card ->
            remain[card]++
        }
//        for (other in allCards) {
//            if (remain[other] >= 0 && remain[other] < 4) {
//                remain[other]++
//                val complete14Card = check14(remain, arbitraryCount)
//                remain[other]--
//                if (complete14Card) {
//                    // 保存所有可能听的牌
//                    waitingCard.add(other)
//                }
//            }
//        }
        stuffOnePossibleCard(allCards, 0, arbitraryCount, waitingCard, remain)
        return waitingCard
    }

    private fun stuffOnePossibleCard(
        allCards: List<Int>,
        depth: Int,
        arbitraryCount: Int,
        waitingCard: MutableList<Int>,
        remain: IntArray,
    ) {
        if (depth >= allCards.size) {
            return
        }
        val cardTaken = allCards[depth]
        if (remain[cardTaken] < 0 || remain[cardTaken] >= 4) {
            return
        }
        val complete14Card = check14(
            inputList = remain.copyOf().also { copy ->
                copy[cardTaken]++
            }, arbitraryCount = arbitraryCount
        )
        if (complete14Card) {
            // 保存所有可能听的牌
            waitingCard.add(cardTaken)
        }
        stuffOnePossibleCard(
            allCards = allCards,
            depth = depth + 1,
            arbitraryCount = arbitraryCount,
            waitingCard = waitingCard,
            remain = remain,
        )
    }

    /**
     * 判断是否胡牌
     */
    private fun check14(inputList: IntArray, arbitraryCount: Int): Boolean {
        // 利用回溯来处理多种情况的判断
        return searchBaseOnDepth(arbitraryCount, inputList, 1, 4, 1)
    }

    /**
     * @param arbitraryCount 总共任意牌的个数
     * @param remain 手牌按桶计数列表
     * @param depth 当前需要决定分支的牌在下标。万筒条下标不会重复，分别在三个区间
     * @param threeCount 还需要满足的三个（顺或刻）的个数
     * @param threeCount 还需要满足的对儿个数
     * */
    private fun searchBaseOnDepth(
        arbitraryCount: Int,
        remain: IntArray,
        depth: Int,
        threeCount: Int,
        twoCount: Int
    ): Boolean {
        val lacks = remain.fold(0) { acc, each -> if (each < 0) acc + each else acc }
        // 允许赊账。但想赊账的个数超过任意牌个数，无法再赊
        if (arbitraryCount > 0 && Math.abs(lacks) > arbitraryCount) {
            return false
        } else if (threeCount < 0 || twoCount < 0) {
            return false
        }
        var i = depth
        val len = remain.size
        while (i < len && remain[i] <= 0) {
            i++;
        }
        if (i >= len) {
            return when {
                threeCount == 0 && twoCount == 0 -> {
                    Math.abs(lacks) == arbitraryCount
                }
                threeCount == 1 && twoCount == 0 -> {
                    // 剩余三张任意牌，可以直接赊账一个顺或者一个刻
                    Math.abs(lacks) + 3 == arbitraryCount
                }
                threeCount == 0 && twoCount == 0 -> {
                    // 剩余两张任意牌，可以直接赊账一个对儿
                    Math.abs(lacks) + 2 == arbitraryCount
                }
                else -> false
            }
        }
        return decideNextDirection(
            arbitraryCount = arbitraryCount,
            remain = remain,
            cardTaken = i,
            threeCount = threeCount,
            twoCount = twoCount
        )
    }

    /**
     * @param arbitraryCount 总共任意牌的个数
     * @param remain 手牌按桶计数列表
     * @param cardTaken 当前需要决定分支的牌在下标。万筒条下标不会重复，分别在三个区间
     * @param threeCount 还需要满足的三个（顺或刻）的个数
     * @param threeCount 还需要满足的对儿个数
     * */
    private fun decideNextDirection(
        arbitraryCount: Int,
        remain: IntArray,
        cardTaken: Int,
        threeCount: Int,
        twoCount: Int,
    ): Boolean {
        val thisCardRemain = remain[cardTaken]
        val i = cardTaken
        if (thisCardRemain < 0) {
            // 本张牌没了，看能不能组下一个
            return searchBaseOnDepth(
                arbitraryCount = arbitraryCount,
                remain = remain, depth = i + 1, threeCount = threeCount - 1, twoCount = twoCount
            )
        }
        val len = remain.size
        // 拿走一对
        // 不可能再出现3条顺的情况 因为等价于3个刻
        val twins = searchBaseOnDepth(
            arbitraryCount = arbitraryCount,
            remain = remain.copyOf().also { copy ->
                copy[i] = copy[i] - 2
            }, depth = i, threeCount = threeCount, twoCount = twoCount - 1
        )
        if (twins) {
            return true
        }
        // 拿走一个刻，可能多余3张，所以下标i不挪
        val triple = searchBaseOnDepth(
            arbitraryCount = arbitraryCount,
            remain = remain.copyOf().also { copy ->
                copy[i] = copy[i] - 3
            }, depth = i, threeCount = threeCount - 1, twoCount = twoCount
        )
        if (triple) {
            return true
        }
        // 字牌没有顺
        if (thisCardRemain > Chars.ONE.internalOffset) {
            return false
        }
        // 赊账接下来2张连续的牌，拿一个顺
        if (i + 2 < len) {
            val threeSequence = searchBaseOnDepth(
                arbitraryCount = arbitraryCount,
                remain = remain.copyOf().also { copy ->
                    copy[i]--
                    copy[i + 1]--
                    copy[i + 2]--
                }, depth = i, threeCount = threeCount - 1, twoCount = twoCount
            )
            if (threeSequence) return true
        }
        return false
    }

    /**
     * @param arbitraryCount 总共任意牌的个数
     * @param remain 手牌按桶计数列表
     * @param cardTaken 当前需要决定分支的牌在下标。万筒条下标不会重复，分别在三个区间
     * @param threeCount 还需要满足的三个（顺或刻）的个数
     * @param threeCount 还需要满足的对儿个数
     * */
    private fun judgeByThisCardRemain(
        arbitraryCount: Int,
        remain: IntArray,
        cardTaken: Int,
        threeCount: Int,
        twoCount: Int,
    ): Boolean {
        val thisCardRemain = remain[cardTaken]
        if (thisCardRemain < 0) {
            return false
        }
        val i = cardTaken
        val len = remain.size
        when (thisCardRemain) {
            1 -> {
                // 赊账自己，凑成一对拿走
                remain[i] = remain[i] - 2
                val twins =
                    searchBaseOnDepth(arbitraryCount, remain, i + 1, threeCount, twoCount - 1)
                remain[i] = remain[i] + 2
                if (twins) {
                    return true
                }
                // 赊账2张连续的牌，拿一个顺
                if (i + 2 < len) {
                    remain[i]--
                    remain[i + 1]--
                    remain[i + 2]--
                    val threeSequence =
                        searchBaseOnDepth(arbitraryCount, remain, i + 1, threeCount - 1, twoCount)
                    remain[i]++
                    remain[i + 1]++
                    remain[i + 2]++
                    if (threeSequence) return true
                }
                return false
            }
            2 -> {
                // 赊账拿走一个刻。虽然目前只有一对，但是尝试赊账一张任意牌成为顺
                remain[i] = remain[i] - 3
                val triple =
                    searchBaseOnDepth(arbitraryCount, remain, i + 1, threeCount - 1, twoCount)
                remain[i] = remain[i] + 3
                if (triple) {
                    return true
                }
                // 拿走一对
                remain[i] = remain[i] - 2
                val twins =
                    searchBaseOnDepth(arbitraryCount, remain, i + 1, threeCount, twoCount - 1)
                remain[i] = remain[i] + 2
                if (twins) {
                    return true
                }
                // 拿走两个顺
                if (i + 2 >= len) {
                    return false
                }
                remain[i] = remain[i] - 2
                remain[i + 1] = remain[i + 1] - 2
                remain[i + 2] = remain[i + 2] - 2
                val doubleThreeSequence =
                    searchBaseOnDepth(arbitraryCount, remain, i + 1, threeCount - 2, twoCount)
                remain[i] = remain[i] + 2
                remain[i + 1] = remain[i + 1] + 2
                remain[i + 2] = remain[i + 2] + 2
                return doubleThreeSequence
            }
            else -> {
                // 拿走一个刻，可能多余3张，所以下标i不挪
                remain[i] = remain[i] - 3
                val triple = searchBaseOnDepth(arbitraryCount, remain, i, threeCount - 1, twoCount)
                remain[i] = remain[i] + 3
                if (triple) {
                    return true
                }
                // 拿走一对
                // 不可能再出现3条顺的情况 因为等价于3个刻
                remain[i] = remain[i] - 2
                // i不用加1
                val twins = searchBaseOnDepth(arbitraryCount, remain, i, threeCount, twoCount - 1)
                remain[i] = remain[i] + 2
                return twins
            }
        }
    }
}