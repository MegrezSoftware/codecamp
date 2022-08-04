package com.practice.majiang

import com.practice.majiang.data.MaJiang

interface IRuleStrategy {

    /**
     * 是否胡牌
     */
    fun checkHu(set: List<MaJiang>, target: MaJiang): Boolean

    /**
     * 是否碰
     */
    fun checkPeng(set: List<MaJiang>, target: MaJiang): Boolean

    /**
     * 是否吃牌
     */
    fun checkChi(set: List<MaJiang>, target: MaJiang): Boolean

}