package com.practice.majiang.game

import com.practice.majiang.data.MaJiang

/**
 * 发牌器
 */
interface IDealer {

    /**
     * 发牌
     */
    fun dealerMaJiang()

    /**
     * 摸一张牌
     */
    fun pickMaJiang():MaJiang

}