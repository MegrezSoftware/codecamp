package com.practice.majiang

import com.practice.majiang.data.MaJiang

/**
 * 发牌器
 */
interface IDealer {

    /**
     * 发牌
     */
    fun dealerMaJiang():List<MaJiang>

    /**
     * 摸一张牌
     */
    fun pickMaJiang():MaJiang

    /**
     * 是否还有剩余的牌
     */
    fun pickOut():Boolean
}